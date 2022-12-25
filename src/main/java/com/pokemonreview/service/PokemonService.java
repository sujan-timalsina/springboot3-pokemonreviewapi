package com.pokemonreview.service;

import com.pokemonreview.dto.PokemonDto;

import java.util.List;

public interface PokemonService {

    PokemonDto createPokemon(PokemonDto pokemonDto);
    List<PokemonDto> getAllPokemon();

    PokemonDto getPokemonById(int id);
}
