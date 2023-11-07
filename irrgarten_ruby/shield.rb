# frozen_string_literal: true
require_relative 'dice'
class Shield
  def initialize(protection = 0.0, uses = 0)
    @protection = protection
    @uses = uses
  end

  def protect
    effective_protection = 0

    if @uses > 0
      effective_protection = @protection
      @uses -= 1
    end

    effective_protection
  end

  def to_s
    "S[#{@protection}, #{@uses}]"
  end

  def discard
    Dice::discard_element(@uses)
  end

end
