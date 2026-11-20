require_relative 'dice'

module Irrgarten
  class CombatElement

    attr_accessor :effect, :uses

    def initialize(effect, uses)
      @effect = effect
      @uses = uses
    end

    def produce_effect
      if @uses > 0
        @uses = @uses-1
        @effect
      else
        0.0
      end
    end

    def discard
      Dice.discard_element(@uses)
    end

    def to_s
      "[#{@effect}, #{@uses}]"
    end
  end
end