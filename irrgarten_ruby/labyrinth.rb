# frozen_string_literal: true

module Irrgarten
  class Labyrinth
    BLOCK_CHAR = 'X'
    EMPTY_CHAR = '-'
    MONSTER_CHAR = 'M'
    COMBAT_CHAR = 'C'
    EXIT_CHAR = 'E'
    ROW = 0
    COL = 1
    @monsters = [[]]
    @players = [[]]
    @labyrinth = [[]]

    def initialize(n_rows, n_cols, exit_row, exit_col)
      @n_rows = n_rows
      @n_cols = n_cols
      @exit_row = exit_row
      @exit_col = exit_col

      @labyrinth = Array.new(n_rows) { Array.new(n_cols, EMPTY_CHAR) }
      @players = Array.new(n_rows) { Array.new(n_cols, nil) }
      @monsters = Array.new(n_rows) { Array.new(n_cols, nil) }

      @labyrinth[exit_row][exit_col] = EXIT_CHAR
    end

    def spread_players(players)
      players.each do |p|
        pos = random_empty_pos
        # puts "Spreading player: #{p}"
        put_player_2d(-1, -1, pos[ROW], pos[COL], p)
      end
    end

    def spread_player_debug(players)
      put_player_2d(-1,-1, 0, 0, players[0])
    end

    def have_a_winner
      !@players[@exit_row][@exit_col].nil?
    end

    def to_s
      complete_matrix_string = ''
      @n_rows.times do |r|
        @n_cols.times do |c|
          complete_matrix_string += "#{@labyrinth[r][c]} | "
        end
        complete_matrix_string.concat("\n")
      end
      complete_matrix_string
    end

    def add_monster(row, col, monster)
      if pos_ok(row, col) && empty_pos(row, col)
        @labyrinth[row][col] = MONSTER_CHAR
        @monsters[row][col] = monster
        monster.set_pos(row, col)
      end
    end

    def put_player(direction, player)
      old_row = player.row
      old_col = player.col

      new_pos = dir_2_pos(old_row, old_col, direction)

      put_player_2d(old_row, old_col, new_pos[ROW], new_pos[COL], player)
    end

    def add_block(orientation, start_row, start_col, length)
      inc_row = 0
      inc_col = 1

      if orientation == Orientation::VERTICAL
        inc_row = 1
        inc_col = 0
      end

      row = start_row
      col = start_col

      while pos_ok(row, col) && (empty_pos(row, col) && length.positive?)
        @labyrinth[row][col] = BLOCK_CHAR
        length -= 1
        row += inc_row
        col += inc_col
      end
    end

    def valid_moves(row, col)
      output = []

      output.push(Directions::DOWN) if can_step_on(row + 1, col)

      output.push(Directions::UP) if can_step_on(row - 1, col)

      output.push(Directions::RIGHT) if can_step_on(row, col + 1)

      output.push(Directions::LEFT) if can_step_on(row, col - 1)

      output
    end

    private

    def pos_ok(row, col)
      (row < @n_rows) && (col < @n_cols) && (row >= 0) && (col >= 0)
    end

    def empty_pos(row, col)
      @labyrinth[row][col] == EMPTY_CHAR
    end

    def monster_pos(row, col)
      @labyrinth[row][col] == MONSTER_CHAR
    end

    def exit_pos(row, col)
      @labyrinth[row][col] == EXIT_CHAR
    end

    def combat_pos(row, col)
      @labyrinth[row][col] == COMBAT_CHAR
    end

    def can_step_on(row, col)
      pos_ok(row, col) && (monster_pos(row, col) || empty_pos(row, col) || exit_pos(row, col))
    end

    def update_old_pos(row, col)
      if pos_ok(row, col)
        if combat_pos(row, col)
          @labyrinth[row][col] = MONSTER_CHAR
        else
          @labyrinth[row][col] = EMPTY_CHAR
        end
      end
    end

    def dir_2_pos(row, col, direction)
      next_position = []

      case direction
      when Directions::UP
        next_position[ROW] = row - 1
        next_position[COL] = col
      when Directions::DOWN
        next_position[ROW] = row + 1
        next_position[COL] = col
      when Directions::LEFT
        next_position[ROW] = row
        next_position[COL] = col - 1
      when Directions::RIGHT
        next_position[ROW] = row
        next_position[COL] = col + 1
      else
        puts('Error en dir_2_pos')
      end

      next_position
    end

    def random_empty_pos
      random_pos = []

      loop do
        random_pos[ROW] = Dice.random_pos(@n_rows)
        random_pos[COL] = Dice.random_pos(@n_cols)
        break if empty_pos(random_pos[ROW], random_pos[COL])
      end

      random_pos
    end

    def put_player_2d(old_row, old_col, row, col, player)
      output = nil

      puts "Se puede pisar en la casilla #{row}, #{col}?: #{can_step_on(row, col)}"
      if can_step_on(row, col)

        if pos_ok(old_row, old_col)
          p = @players[old_row][old_col]

          if p == player
            update_old_pos(old_row, old_col)
            @players[old_row][old_col] = nil
          end
        end

        if monster_pos(row, col)
          @labyrinth[row][col] = COMBAT_CHAR
          output = @monsters[row][col]
        else
          @labyrinth[row][col] = player.number.to_s
        end

        @players[row][col] = player
        player.set_pos(row, col)
      end

      output
    end
  end
end
