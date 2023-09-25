# frozen_string_literal: true

class Weapon
  @protection
  @uses

Shield(protection, uses)
  if protection < 0 || uses < 0
    throw new IllegalArgumentException("Parámetros incorrectos.")
  end

  this.protection = protection
  this.uses = uses
  end

  Shield
    this(0.0f, 0)
  end

  protect

    effectiveProtection = 0

    if (uses > 0){
      effectiveProtection = protection
    uses--
    end

    return effectiveProtection
  end

  toString
    return "S[" + protection + ", " + uses + "]"
  end

  discard
    return Dice.discardElement(uses)
  end
end
