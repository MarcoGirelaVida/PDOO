# frozen_string_literal: true

class GameState
  def initialize(labyrinthv, players, monsters, current_player, winner, log)
    @labyrinthv = labyrinthv
    @players = players
    @monsters = monsters
    @current_player = current_player
    @winner = winner
    @log = log
  end

  attr_reader :labyrinthv
  attr_reader :players
  attr_reader :monsters
  attr_reader :current_player
  attr_reader :winner
  attr_reader :log

end
