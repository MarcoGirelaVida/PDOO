# frozen_string_literal: true

module Irrgarten
  require_relative 'dice'
  class Shield
    def initialize(protection = Dice.shield_power, uses = Dice.uses_left)
      @protection = protection
      @uses = uses
    end

    def protect
      effective_protection = 0

      if @uses.positive?
        effective_protection = @protection
        @uses -= 1
      end

      effective_protection
    end

    def to_s
      "S[#{@protection}, #{@uses}]"
    end

    def discard
      Dice.discard_element(@uses)
    end
  end
end
