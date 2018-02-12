/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.units.malelab.jgea.core.evolver.stopcondition;

import it.units.malelab.jgea.core.listener.event.EvolutionEvent;
import it.units.malelab.jgea.core.mapper.CachedMapper;
import it.units.malelab.jgea.core.mapper.Mapper;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author eric
 */
public class RelativeElapsedTime<G, S, F> implements StopCondition<G, S, F> {
  
  private final double r;
  private final CachedMapper<S, F> cachedFitnessMapper;

  public RelativeElapsedTime(double r, CachedMapper<S, F> cachedFitnessMapper) {
    this.r = r;
    this.cachedFitnessMapper = cachedFitnessMapper;
  }

  public double getR() {
    return r;
  }

  public CachedMapper<S, F> getCachedFitnessMapper() {
    return cachedFitnessMapper;
  }

  @Override
  public boolean shouldStop(EvolutionEvent<G, S, F> evolutionEvent) {
    double elapsedNanos = TimeUnit.NANOSECONDS.convert(evolutionEvent.getElapsedMillis(), TimeUnit.MILLISECONDS);
    double avgNanos = cachedFitnessMapper.getCacheStats().averageLoadPenalty();
    return elapsedNanos/avgNanos>r;
  }

}