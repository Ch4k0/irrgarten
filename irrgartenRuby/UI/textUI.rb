
require 'io/console'
require_relative '../directions'

module Irrgarten
  module UI

    class TextUI

      #https://gist.github.com/acook/4190379
      def read_char
        STDIN.echo = false
        STDIN.raw!

        input = STDIN.getc.chr
        if input == "\e"
          input << STDIN.read_nonblock(3) rescue nil
          input << STDIN.read_nonblock(2) rescue nil
        end
      ensure
        STDIN.echo = true
        STDIN.cooked!

        return input
      end

      def next_move
        print "Where? (w/a/s/d) "
        got_input = false
        output = nil

        while !got_input
          c = gets.chomp.downcase

          case c
          when 'w'
            output = Irrgarten::Directions::UP
            got_input = true
          when 's'
            output = Irrgarten::Directions::DOWN
            got_input = true
          when 'd'
            output = Irrgarten::Directions::RIGHT
            got_input = true
          when 'a'
            output = Irrgarten::Directions::LEFT
            got_input = true
          when 'c'
            puts "CONTROL-C"
            exit(1)
          else
            puts "Invalid input"
          end
        end

        output
      end











      def show_game(game_state)
        puts game_state.labyrinth
        puts game_state.players
      end

    end # class

  end # module
end

