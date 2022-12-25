package com.pokemonreview.service.implementation;

import com.pokemonreview.Repository.PokemonRepository;
import com.pokemonreview.dto.PokemonDto;
import com.pokemonreview.exception.ResourceNotFoundException;
import com.pokemonreview.model.Pokemon;
import com.pokemonreview.service.PokemonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {

    private PokemonRepository pokemonRepository;

    public PokemonServiceImpl(PokemonRepository pokemonRepository){
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto){
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon newPokemon = pokemonRepository.save(pokemon);
        return mapToDto(newPokemon);
    }

    @Override
    public List<PokemonDto> getAllPokemon(){
        List<Pokemon> pokemons= pokemonRepository.findAll();
        return pokemons.stream().map(pokemon -> mapToDto(pokemon)).collect(Collectors.toList());
    }

    @Override
    public PokemonDto getPokemonById(int id){
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pokemon doesn't exist of id: "+id));
        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemon(PokemonDto pokemonDto, int id) {
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pokemon doesn't exist of id: "+id+" for the update"));
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon updatedPokemon = pokemonRepository.save(pokemon);
        return mapToDto(updatedPokemon);
    }

    private PokemonDto mapToDto(Pokemon pokemon){
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setName(pokemon.getName());
        pokemonDto.setType(pokemon.getType());
        return pokemonDto;
    }

    private Pokemon mapToEntity(PokemonDto pokemonDto){
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        return pokemon;
    }
}
//map returns a new list rather than only looping
//Changed from <Pokemon> generic list to <PokemonDto> typed list on method getAllPokemon() using stream, map and collect