require_relative 'Player'
require_relative 'Dice'
require_relative 'Labyrinth'
require_relative 'Monster'
require_relative 'GameState'
require_relative 'GameCharacter'
require_relative 'FuzzyPlayer'

module Irrgarten
  class Game
    @@MAXROUNDS = 10

    def initialize(n_players)
      @log = ""

      @players = []
      @monsters = []

      n_players.times do |i|
        aux_p = Player.new((i+48).chr, Dice.random_intelligence, Dice.random_strenght)
        @players << aux_p
      end

      @current_player_index = 0
      @current_player = @players[@current_player_index]

      /
      @labyrinth = Labyrinth.new(
        Dice.random_pos(10),
        Dice.random_pos(10),
        Dice.random_pos(10),
        Dice.random_pos(10)
      )
      /

      @labyrinth = Irrgarten::Labyrinth.new(10, 10, 1, 0)

      configure_labyrinth
      @labyrinth.spread_players(@players)
    end

    def finished?
      @labyrinth.have_a_winner?
    end

    def next_step(preferred_direction)
      @log = ""
      dead = @current_player.dead?

      if !dead
        direction = actual_direction(preferred_direction)

        if direction != preferred_direction
          log_player_no_orders
        end

        monster = @labyrinth.put_player(direction, @current_player)

        if monster.nil?
          log_no_monster
          puts "NoMonster"
        else
          winner = combat(monster)
          manage_reward(winner)
        end

      else
        manage_resurrection
      end

      end_game = finished?

      next_player unless end_game

      end_game
    end

    def get_game_state
      s_players = ""
      s_monsters = ""

      @monsters.each do |m|
        s_monsters += m.to_s
        s_monsters += "\n"
      end

      @players.each do |p|
        s_players += p.to_s
        s_players += "\n"
      end

      GameState.new(@labyrinth.to_s, s_players, s_monsters, @current_player_index, finished?, @log)
    end

    def configure_labyrinth
      @labyrinth.add_block(:horizontal, 2, 1, 3)
      @labyrinth.add_block(:vertical, 2, 4, 5)
      @labyrinth.add_block(:horizontal, 5, 2, 5)
      @labyrinth.add_block(:vertical, 3, 7, 4)
      @labyrinth.add_block(:vertical, 3, 6, 2)
      @labyrinth.add_block(:vertical, 8, 5, 1)

      m1 = Monster.new("Pepe", 3, 5)
      m2 = Monster.new("Luca", 2, 6)
      m3 = Monster.new("Juan", 4, 3)

      @labyrinth.add_monster(7, 1, m1)
      @labyrinth.add_monster(8, 8, m2)
      @labyrinth.add_monster(5, 2, m3)
    end

    def next_player
      @current_player_index += 1
      if @current_player_index == @players.size
        @current_player_index = 0
      end
      @current_player = @players[@current_player_index]
    end

        def actual_direction(preferred_direction)
          currentRow = @current_player.row
          currentCol = @current_player.col

          validMoves = @labyrinth.valid_moves(currentRow, currentCol)

          output = @current_player.move(preferred_direction, validMoves)
        end

        def combat(monster)
          rounds = 0
          winner = GameCharacter::PLAYER
          lose = false

          player_attack = @current_player.attack
          lose = monster.defend(player_attack)

          while !lose && rounds < @@MAXROUNDS
            winner = GameCharacter::MONSTER
            rounds += 1

            monster_attack = monster.attack
            lose = @current_player.defend(monster_attack)

            if !lose
              player_attack = @current_player.attack
              winner = GameCharacter::PLAYER
              lose = monster.defend(player_attack)
            end
          end
          log_rounds(rounds,@@MAXROUNDS)
          winner
        end

        def manage_reward(winner)
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
            fuzzy = FuzzyPlayer.new(@current_player)
            @players[@current_player_index] = fuzzy
            @current_player = fuzzy;
            log_resurrected
          else
            log_player_skip_turn
          end
        end

    def log_player_won
      @log += "El jugador #{@current_player.number} ha ganado el combate.\n"
    end

    def log_monster_won
      @log += "El monstruo ha ganado el combate.\n"
    end

    def log_resurrected
      @log += "El jugador #{@current_player.number} ha resucitado.\n"
    end

    def log_player_skip_turn
      @log += "El jugador #{@current_player.number} ha perdido el turno por estar muerto.\n"
    end

    def log_player_no_orders
      @log += "El jugador #{@current_player.number} no ha podido seguir las órdenes.\n"
    end

    def log_no_monster
      @log += "El jugador #{@current_player.number} se ha movido a una celda vacía o no se ha podido mover.\n"
    end

    def log_rounds(rounds, max)
      @log += "Se han producido #{rounds} de #{max} rondas de combate.\n"
    end
  end
end