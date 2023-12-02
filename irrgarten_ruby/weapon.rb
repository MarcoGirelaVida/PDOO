# frozen_string_literal: true

module Irrgarten
  require_relative 'dice'
  class Weapon
    def initialize(power = Dice.weapon_power, uses = Dice.uses_left)
      @power = power
      @uses = uses
    end

    def attack
      effective_power = 0

      if @uses.positive?
        effective_power = @power
        @uses -= 1
      end

      effective_power
    end

    def to_s
      "W[#{@power}, #{@uses}]"
    end

    def discard
      Dice.discard_element(@uses)
    end
  end
end
