# frozen_string_literal: true

class GameState
  def initialize(labyrinthv = '', players = '', monsters = '', current_player = 0, winner = false, log = '')
    @labyrinthv = labyrinthv
    @players = players
    @monsters = monsters
    @current_player = current_player
    @winner = winner
    @log = log
  end

  attr_reader :labyrinthv, :players, :monsters, :current_player, :winner, :log

  def get_log
    output
    output.concat("CURRENT GAME STATE:\n")
    output.concat("CURRENT PLAYER: #{@current_player}\n")
    output.concat("PLAYERS: #{@players}\n")
    output.concat("MONSTERS: #{@monsters}\n")
    output.concat("CURRENT PLAYER: #{@current_player}\n")
    output.concat("IS THERE A WINNER?: #{@winner}\n")
    output.concat("LOG: #{@log}\n")
    output
  end
end
