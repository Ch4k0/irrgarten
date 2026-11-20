require_relative 'Weapon'
require_relative 'Shield'
require_relative 'LabyrinthCharacter'

module Irrgarten
  class Player < LabyrinthCharacter
    attr_reader :row, :col, :number, :consecutive_hits, :weapons, :shields

    @@MAX_WEAPONS = 2
    @@MAX_SHIELDS = 3
    @@INITIAL_HEALTH = 1
    @@HITS2LOSE = 3

    def initialize(number, intelligence, strength)
      super("Player ##{number}", intelligence, strength, @@INITIAL_HEALTH)
      @number = number
      @consecutive_hits = 0
      @row = -1
      @col = -1

      @weapons = []
      @shields = []
    end

    def copia(other)
      super(other)
      @number = other.number
      @consecutive_hits = other.consecutive_hits
      @weapons = other.weapons.dup
      @shields = other.shields.dup
    end

    def resurrect
      @weapons.clear
      @shields.clear
      @health = @@INITIAL_HEALTH
      @consecutive_hits = 0
    end

    def move(direction, valid_moves)
      if valid_moves.size > 0 && !valid_moves.include?(direction)
        valid_moves[0]
      else
        direction
      end
    end

    def attack
      @strength + sum_weapons
    end

    def defend(received_attack)
      manage_hit(received_attack)
    end

    def receive_reward
      w_reward = Dice.weapons_reward
      s_reward = Dice.shields_reward

      for i in 0..w_reward
        receive_weapon(new_weapon)
      end
      for i in 0..s_reward
        receive_shield(new_shield)
      end

      @health += Dice.health_reward
    end

    def to_s

      salida = super
      salida += "Golpes consecutivos: #{@consecutive_hits}\n"

      @weapons.each do |w|
        salida += w.to_s + "\n"
      end

      @shields.each do |s|
        salida += s.to_s + "\n"
      end

      salida
    end

    private

    def receive_weapon(w)
      @weapons.reject! { |wi| wi.discard }

      if @weapons.size < @@MAX_WEAPONS
        @weapons << w
      end
    end

    def receive_shield(s)
      @shields.reject! { |si| si.discard }

      if @shields.size < @@MAX_SHIELDS
        @shields << s
      end
    end

    def new_weapon
      pow = Dice.weapon_power
      uses = Dice.uses_left
      Weapon.new(pow, uses)
    end

    def new_shield
      prot = Dice.shield_power
      uses = Dice.uses_left
      Shield.new(prot, uses)
    end

    def sum_weapons
      sum = 0.0
      @weapons.each do |w|
        sum += w.attack
      end
      sum
    end

    def sum_shields
      sum = 0.0
      @shields.each do |s|
        sum += s.protect
      end
      sum
    end

    def defensive_energy
      @intelligence + sum_shields
    end

    def manage_hit(received_attack)
      defense = defensive_energy
      if defense < received_attack
        got_wounded
        inc_consecutive_hits
      else
        reset_hits
      end
      if @consecutive_hits == @@HITS2LOSE || dead?
        reset_hits
        true
      else
        false
      end
    end

    def reset_hits
      @consecutive_hits = 0
    end

    def got_wounded
      @health -= 1
    end

    def inc_consecutive_hits
      @consecutive_hits += 1
    end

  end
end