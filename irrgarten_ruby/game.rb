# frozen_string_literal: false

module Irrgarten
  require_relative 'player'
  require_relative 'monster'
  require_relative 'game_state'
  require_relative 'labyrinth'
  require_relative 'weapon'
  require_relative 'shield'
  require_relative 'dice'
  require_relative 'orientation'
  require_relative 'game_character'
  class Game
    MAX_ROUNDS = 10
    N_ROWS = 20
    N_COLS = 20
    EXIT_ROW = Dice.random_pos(N_ROWS)
    EXIT_COL = Dice.random_pos(N_COLS)
    def initialize(n_players = 1, player_intelligence = Dice.random_intelligence, player_strength = Dice.random_strength,
                   monster_intelligence = Dice.random_intelligence, monster_strength = Dice.random_strength)
      @players = []
      @monsters = []

      n_players.times do |i|
        p = Player.new(i, player_intelligence, player_strength)
        @players.push(p)
      end

      @current_player_index = Dice.who_starts(n_players)
      @current_player = @players.at(@current_player_index)

      @labyrinth = Labyrinth.new(N_ROWS, N_COLS, EXIT_ROW, EXIT_COL)

      configure_labyrinth(monster_intelligence, monster_strength)
      @labyrinth.spread_players(@players)
      @log = "GAME STARTS\n"
      game_state
    end

    def finished
      @labyrinth.have_a_winner
    end

    def next_step(preferred_direction)
      @log = ''
      dead = @current_player.dead
      if !dead
        direction = actual_direction(preferred_direction)

        log_player_no_orders if direction != preferred_direction

        monster = @labyrinth.put_player(direction, @current_player)

        if monster.nil?
          log_no_monster
        else
          winner = combat(monster)
          manage_reward(winner)
        end
      else
        manage_resurrection
      end

      end_game = finished

      next_player unless end_game

      end_game
    end

    def game_state
      players_string = ''
      @players.each do |player|
        players_string += player.to_s
      end

      monster_string = ''
      @monsters.each do |monster|
        monster_string += monster.to_s
      end

      GameState.new(@labyrinth.to_s, players_string, monster_string, @current_player.to_s, finished, @log)
    end

    def configure_labyrinth(monster_intelligence, monster_strength)
      monster_counter = 0
      N_ROWS.times do |row|
        N_COLS.times do |col|
          next unless (row != EXIT_ROW) && (col != EXIT_COL)

          element_spawned = Dice.spawn_monster_or_block
          if element_spawned == 2
            @labyrinth.add_block(Orientation::VERTICAL, row, col, 1)

          elsif element_spawned == 1
            new_monster = Monster.new("Monster #{monster_counter}", monster_intelligence, monster_strength)
            @labyrinth.add_monster(row, col, new_monster)
            @monsters.push(new_monster)
            monster_counter += 1
          end
        end
      end
    end

    def next_player
      @current_player_index = (@current_player_index + 1) % @players.size
      @current_player = @players[@current_player_index]
    end

    def actual_direction(preferred_direction)
      current_row = @current_player.row
      current_col = @current_player.col
      valid_moves = @labyrinth.valid_moves(current_row, current_col)

      @current_player.move(preferred_direction, valid_moves)
    end

    def combat(monster)
      rounds = 0
      winner = GameCharacter::PLAYER

      player_attack = @current_player.attack
      lose = monster.defend(player_attack)

      while !lose && (rounds < MAX_ROUNDS)
        rounds += 1
        winner = GameCharacter::MONSTER

        monster_attack = monster.attack
        lose = @current_player.defend(monster_attack)

        next if lose

        player_attack = @current_player.attack
        winner = GameCharacter::PLAYER
        lose = monster.defend(player_attack)
      end

      log_rounds(rounds, MAX_ROUNDS)

      winner
    end

    def manage_reward(winner)
      if winner == GameCharacter::PLAYER
        @current_player.receive_reward
        log_player_won
      else
        log_monster_won
      end
    end

    def manage_resurrection
      resurrect = Dice.resurrect_player

      if resurrect
        @current_player.resurrect
        log_resurrected
      else
        log_player_skip_orders
      end
    end

    def log_player_won
      @log.concat("PLAYER WON THE BATTLE\n")
    end

    def log_monster_won
      @log.concat("MONSTER WON THE BATTLE\n")
    end

    def log_resurrected
      @log.concat("PLAYER HAS RESURRECTED\n")
    end

    def log_player_skip_orders
      @log.concat("DEAD PLAYER. SKIPPED\n")
    end

    def log_player_no_orders
      @log.concat("INVALID ORDER. PLAYER DID NOT COMMITTED TO IT\n")
    end

    def log_no_monster
      @log.concat("PLAYER MOVED TO A VOID SQUARE OR DID NOT MOVE\n")
    end

    def log_rounds(rounds, max)
      @log.concat("THE COMBAT HAVE GOT: #{rounds} of #{max} ROUNDS\n")
    end
  end
end
