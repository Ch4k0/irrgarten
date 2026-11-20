require_relative 'CombatElement'

module Irrgarten
  class Shield < CombatElement

    def initialize(protection, uses)
      super(protection, uses)
    end

    def protect
      produce_effect
    end

    def to_s
      "S" + super
    end
  end
end