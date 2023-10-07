# frozen_string_literal: true

class Dice
  MAX_USES = 5
  MAX_INTELLIGENCE = 10.0
  MAX_STRENGTH = 10.0
  RESURRECT_PROB = 0.3
  WEAPONS_REWARD = 2
  SHIELDS_REWARD = 3
  HEALTH_REWARD = 5
  MAX_ATTACK = 3
  MAX_SHIELD = 2
  GENERATOR = Random.new

  def self.random_pos(max)
    GENERATOR.rand(max.to_i+1)
  end

  def self.who_starts(players)
    GENERATOR.rand(players.to_i+1)
  end
  def self.random_intelligence
    GENERATOR.rand(MAX_INTELLIGENCE)
  end

  def self.random_strength
    GENERATOR.rand(MAX_STRENGTH)
  end

  def self.resurrect_player
    RESURRECT_PROB >= GENERATOR.rand
  end

  def self.weapons_reward
    GENERATOR.rand(WEAPONS_REWARD+1)
  end

  def self.shields_reward
    GENERATOR.rand(SHIELDS_REWARD+1)
  end

  def self.health_reward
    GENERATOR.rand(HEALTH_REWARD+1)
  end

  def self.weapon_power
    GENERATOR.rand(MAX_ATTACK.to_f)
  end

  def self.shield_power
    GENERATOR.rand(MAX_SHIELD.to_f)
  end

  def self.uses_left
    GENERATOR.rand(MAX_USES+1)
  end

  def self.intensity(competence)
    GENERATOR.rand(competence.to_f)
  end

  def self.discard_element(uses_left)
    !(GENERATOR.rand(MAX_USES) < uses_left)
  end
end
