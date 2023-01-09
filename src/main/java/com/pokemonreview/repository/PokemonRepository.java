package com.pokemonreview.repository;

import com.pokemonreview.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer>{
}
