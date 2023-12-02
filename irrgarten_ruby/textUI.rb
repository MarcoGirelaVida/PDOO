# frozen_string_literal: true

require 'io/console'
require_relative 'directions'
require_relative 'game_state'

module UI
  class TextUI
    # https://gist.github.com/acook/4190379
    def read_char
      $stdin.echo = false
      $stdin.raw!

      input = $stdin.getch
      if input == "\e"
        begin
          input << $stdin.read_nonblock(3)
        rescue StandardError
          nil
        end
        begin
          input << $stdin.read_nonblock(2)
        rescue StandardError
          nil
        end
      end
    ensure
      $stdin.echo = true
      $stdin.cooked!

      return input
    end

    def next_move
      print 'Where? '
      got_input = false
      until got_input
        c = read_char
        case c
        when 'w'
          puts 'UP ARROW'
          output = Irrgarten::Directions::UP
          got_input = true
        when 's'
          puts 'DOWN ARROW'
          output = Irrgarten::Directions::DOWN
          got_input = true
        when 'd'
          puts 'RIGHT ARROW'
          output = Irrgarten::Directions::RIGHT
          got_input = true
        when 'a'
          puts 'LEFT ARROW'
          output = Irrgarten::Directions::LEFT
          got_input = true
        when "\u0003"
          puts 'CONTROL-C'
          got_input = true
          exit(1)
        else
          # error
        end
      end
      output
    end

    def show_game(game_state)
      puts "CURRENT GAME STATE\n"
      puts "MONSTERS:\n #{game_state.monsters}\n"
      puts "PLAYERS\n:#{game_state.players}\n"
      puts "LABYRINTH:\n#{game_state.labyrinth}\n"
      puts "CURRENT PLAYER: #{game_state.current_player}\n"
      puts "IS THERE A WINNER?: #{game_state.winner}\n"
      puts "LOG:\n#{game_state.log}\n"
    end
  end
end
