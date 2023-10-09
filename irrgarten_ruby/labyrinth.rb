# frozen_string_literal: true

class Labyrinth
  BLOCK_CHAR = 'X'
  EMPTY_CHAR = '-'
  MONSTER_CHAR = 'M'
  COMBAT_CHAR = 'C'
  EXIT_CHAR = 'E'
  ROW = 0
  COL = 1
  @@monsters = []
  @@players = []
  @@labyrinth = []

  def initialize(n_rows, n_cols, exit_row, exit_col)
    @n_rows = n_rows
    @n_cols = n_cols
    @exit_row = exit_row
    @exit_col = exit_col

    n_rows.times do |r|
      n_cols do |c|
        @@labyrinth.
      end
    end
  end
end
