# frozen_string_literal: true

class Labyrinth
  BLOCK_CHAR = 'X'
  EMPTY_CHAR = '-'
  MONSTER_CHAR = 'M'
  COMBAT_CHAR = 'C'
  EXIT_CHAR = 'E'
  ROW = 0
  COL = 1
  @@monsters = [[]]
  @@players = [[]]
  @@labyrinth = [[]]

  def initialize(n_rows, n_cols, exit_row, exit_col)
    @n_rows = n_rows
    @n_cols = n_cols
    @exit_row = exit_row
    @exit_col = exit_col

    @@labyrinth = Array.new(n_rows) { Array.new(n_cols, EMPTY_CHAR) }
    @@players = Array.new(n_rows) { Array.new(n_cols, nil) }
    @@monsters = Array.new(n_rows) { Array.new(n_cols, nil) }

    @@labyrinth[exit_row][exit_col] = EXIT_CHAR
  end

  def spread_players (players)
    players.times do |p|
      pos = random_empty_pos
      put_player_2D(-1, -1, pos[0], pos[1], p)
    end
  end

  def have_a_winner
    @@players[@exit_row][@exit_col] != nil
  end

  def to_s
    complete_matrix_string = ''
    @n_rows.times do |r|
      @n_cols.times do |c|
        complete_matrix_string.concat(@@labyrinth[r][c], ' | ')
      end
      complete_matrix_string.concat("\n")
    end
  end
end

