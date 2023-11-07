# frozen_string_literal: true

class Monster
  INITIAL_HEALTH = 5

  def initialize(name, intelligence)
    @name = name
    @intelligence = intelligence
    @health = INITIAL_HEALTH
    @strength = Dice.random_strength
    @row = -1
    @col = -1
  end

  def dead
    @health.zero?
  end

  def attack
    Dice.intensity(@strength)
  end

  def defend(received_attack)
    is_dead = dead

    if is_dead == false
      defensive_energy = Dice.intensity(@intelligence)

      if defensive_energy < received_attack
        got_wounded
        is_dead = dead
      end
    end

    is_dead
  end

  def set_pos(row, col)
    @row = row
    @col = col
  end

  def to_s
    header_string = "MONSTER: #{@name}\n"
    location_string = "POSITION: [#{@row}, #{@col}]\n"
    strength_string = "STRENGTH: #{@strength}\n"
    health_string = "HEALTH: #{@health}\n"
    intelligence_string = "INTELLIGENCE: #{@intelligence}\n"

    header_string + location_string + strength_string + health_string + intelligence_string
  end

  def got_wounded
    @health -= 1
  end
end
