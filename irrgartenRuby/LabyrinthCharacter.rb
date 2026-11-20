module Irrgarten
  class LabyrinthCharacter

    attr_reader :name, :intelligence, :strength, :health, :row, :col
    attr_writer :health

    def initialize(name, intelligence, strength, health)
      @name = name
      @intelligence = intelligence.to_f
      @strength = strength.to_f
      @health = health.to_f
      @row = -1
      @col = -1
    end

    def copia(other)
      @name = other.name
      @intelligence = other.intelligence
      @strength = other.strength
      @health = other.health
      @row = other.row
      @col = other.col
    end

    def dead?
      @health <= 0
    end

    def set_pos(row, col)
      @row = row
      @col = col
    end

    protected def got_wounded
      @health -= 1
    end

    def attack
      raise NotImplementedError
    end

    def defend(attack)
      raise NotImplementedError
    end

    def to_s
        "Nombre: #{@name}\n" +
        "Inteligencia: #{@intelligence}\n" +
        "Fuerza: #{@strength}\n" +
        "Vida: #{@health}\n" +
        "Posición: [#{@row}, #{@col}]\n"
    end
  end
end