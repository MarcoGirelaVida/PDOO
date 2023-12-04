# frozen_string_literal: true

module MAIN
  require_relative 'textUI'
  require_relative 'controller'
  require_relative 'game'
  require_relative 'dice'
  class Main
    def self.main
      vista = UI::TextUI.new
      game = Irrgarten::Game.new(2)
      controller = Control::Controller.new(game, vista)
      controller.play
    end

    def self.main_debug
      vista = UI::TextUI.new
      game = Irrgarten::Game.new(1, 1)
      controller = Control::Controller.new(game, vista)
      controller.play
    end
  end
end

MAIN::Main.main_debug
