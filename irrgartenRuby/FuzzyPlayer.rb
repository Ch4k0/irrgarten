require_relative 'Player'
require_relative 'Dice'

module Irrgarten
  class FuzzyPlayer < Player

    def initialize(other)
      super(other.number, other.intelligence, other.strength)
      copia(other)
    end

    def move(direction, valid_moves)
      pref = Dice.next_step(direction, valid_moves, @intelligence)
      super(pref, valid_moves)
    end

    def attack
      sum_weapons + Dice.intensity(@strength)
    end

    protected def defensive_energy
      sum_shields + Dice.intensity(@intelligence)
    end

    def to_s
      "Fuzzy " + super
    end
  end
end