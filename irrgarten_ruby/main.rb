# frozen_string_literal: true

require_relative 'textUI'
require_relative 'controller'
require_relative 'game'
def main
  vista = UI::TextUI.new
  game = Irrgarten::Game.new(1, 0, 0, 5, 5)
  controller = Control::Controller.new(game, vista)
  controller.play
end
main
