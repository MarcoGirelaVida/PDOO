# frozen_string_literal: true

class Game
  MAX_ROUNDS = 10

  def initialize (n_players)
    @players = Array.new
    @monsters = Array.new

    n_players.times do |i|
      p = Player.new(i, Dice.random_intelligence, Dice.random_strength)
      @players.push(p)
    end

    @current_player_index = Dice.who_starts(n_players)
    @current_player = @players.at(@current_player_index)
    @labyrinth = Labyrinth.new(20,20, Dice.random_pos(20), Dice.random_pos(20))

    @labyrinth.spread_players(@players)

    @log = "GAME STARTS\n"
    game_state = get_game_state
    @log.concat(game_state.get_log)
  end

  def finished
    @labyrinth.have_a_winner
  end

  def next_step (preferred_direction)
    # @log = ""
    dead = @current_player.dead
    if !dead
      direction = actual_direction(preferred_direction)

      if direction != preferred_direction
        log_player_no_orders
      end

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

    unless end_game
      next_player
    end

    end_game
  end

  def get_game_state
    GameState.new(@labyrinth.to_s, @players.to_s, @monsters.to_s, @current_player.to_s, finished, @log)
  end

  def configure_labyrinth
    puts("TODO")
  end

  def next_player
    @current_player_index = (@current_player_index + 1) % @players.size
    @current_player = @players.get(@current_player_index)
  end

  def actual_direction (preferred_direction)
    current_row = @current_player.row
    current_col = @current_player.col
    valid_moves = @labyrinth.valid_moves(current_row, current_col)

    @current_player.move(preferred_direction, valid_moves)
  end

  def combat (monster)
    rounds = 0
    winner = GameCharacter::PLAYER

    player_attack = @current_player.attack
    lose = monster.defend(player_attack)

    while (!lose) && (rounds < MAX_ROUNDS)
      winner = GameCharacter::MONSTER
      rounds += 1

      monster_attack = monster.attack
      lose = @current_player.defend(monster_attack)

      unless lose
        player_attack = @current_player.attack
        winner = GameCharacter::PLAYER
        lose = monster.defend(player_attack)
      end
    end

    log_rounds(rounds, MAX_ROUNDS)

    winner
  end

  def manage_reward (winner)
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

  def log_rounds (rounds, max)
    @log.concat("THE COMBAT HAVE GOT: #{rounds} of #{max} ROUNDS\n")
  end
end
