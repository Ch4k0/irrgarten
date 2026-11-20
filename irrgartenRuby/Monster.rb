require_relative 'LabyrinthCharacter'

module Irrgarten
  class Monster < LabyrinthCharacter
    @@INITIAL_HEALTH = 5

    def initialize(name, intelligence, strength)
      super(name,intelligence,strength, @@INITIAL_HEALTH)
    end

    def attack
      Dice.intensity(@strength)
    end

    def set_pos(row, col)
      @row = row
      @col = col
    end

    def defend(received_attack)
      if dead?
        defensive_energy = Dice.intensity(@intelligence)
        if defensive_energy < received_attack
          got_wounded
        end
      end
      dead?
    end

    def to_s
      "\tMonstruo\n" + super
    end

  end
end