# frozen_string_literal: true
require_relative 'dice'
class Weapon
  def initialize(power = 0.0, uses = 0)
    @power = power
    @uses = uses
  end

  def attack
    effective_power = 0

    if @uses > 0
      effective_power = @power
      @uses -= 1
    end

    effective_power
  end

  def to_s
    "W[#{@power}, #{@uses}]"
  end

  def discard
    Dice::discard_element(@uses)
  end

end
