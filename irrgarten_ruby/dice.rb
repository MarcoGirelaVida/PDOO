# frozen_string_literal: true

module Irrgarten
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
    MONSTER_SPAWN_PROBABILITY = 0.1
    BLOCK_SPAWN_PROBABILITY = 0.3
    GENERATOR = Random.new

    def self.random_pos(max)
      GENERATOR.rand(max.to_i)
    end

    def self.who_starts(players)
      GENERATOR.rand(players.to_i)
    end

    def self.random_intelligence
      GENERATOR.rand(0.0...MAX_INTELLIGENCE)
    end

    def self.random_strength
      GENERATOR.rand(0.0...MAX_STRENGTH)
    end

    def self.resurrect_player
      GENERATOR.rand < RESURRECT_PROB
    end

    def self.weapons_reward
      GENERATOR.rand(0..WEAPONS_REWARD)
    end

    def self.shields_reward
      GENERATOR.rand(0..SHIELDS_REWARD)
    end

    def self.health_reward
      GENERATOR.rand(0..HEALTH_REWARD)
    end

    def self.weapon_power
      GENERATOR.rand(0.0..MAX_ATTACK.to_f)
    end

    def self.shield_power
      GENERATOR.rand(0.0..MAX_SHIELD.to_f)
    end

    def self.uses_left
      GENERATOR.rand(0..MAX_USES)
    end

    def self.intensity(competence)
      GENERATOR.rand(0.0..competence.to_f)
    end

    def self.discard_element(uses_left)
      GENERATOR.rand(MAX_USES) >= uses_left
    end

    def self.spawn_monster_or_block
      result = GENERATOR.rand
      if result <= BLOCK_SPAWN_PROBABILITY
        2
      elsif result <= BLOCK_SPAWN_PROBABILITY + MONSTER_SPAWN_PROBABILITY
        1
      else
        0
      end
    end
  end
end
