package com.polykek.database.services;

import com.polykek.database.entities.*;
import com.polykek.database.repositories.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.springframework.stereotype.Service;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DatabaseService {
    private final CellAutomatonRepository cellAutomatonRepository;
    private final CellRepository cellRepository;
    private final FinalStateRepository finalStateRepository;
    private final GridRepository gridRepository;
    private final IntermediateStateRepository intermediateStateRepository;
    private final PartitionRepository partitionRepository;
    private final ScaRepository scaRepository;
    private final ScienceRepository scienceRepository;
    private final StartStateRepository startStateRepository;
    private final StepRepository stepRepository;
    private final StructureRepository structureRepository;
    private final TransformRuleRepository transformRuleRepository;
    private final EntitySaver entitySaver;

    private void deleteAllData() {
        stepRepository.deleteAll();
        cellAutomatonRepository.deleteAll();
        cellRepository.deleteAll();
        finalStateRepository.deleteAll();
        gridRepository.deleteAll();
        intermediateStateRepository.deleteAll();
        partitionRepository.deleteAll();
        scaRepository.deleteAll();
        scienceRepository.deleteAll();
        startStateRepository.deleteAll();
        structureRepository.deleteAll();
        transformRuleRepository.deleteAll();
    }
    public void fillData() {
        deleteAllData();

        List<TransformRule> transformRules = fillTransformRule(30);
        List<StartState> startStates = fillStartState(40);
        List<Grid> grids = fillGrid(20);

        List<CellAutomaton> cellAutomatons = fillCellAutomaton(transformRules, startStates, grids);

        List<Science> sciences = fillScience(10);
        List<SCA> scas = fillSca(sciences, cellAutomatons);

        List<Step> steps = fillStep(cellAutomatons);

        List<FinalState> finalStates = fillFinalState(30);
        List<IntermediateState> intermediateStates = fillIntermediateState(startStates, finalStates);

        List<Structure> structures = fillStructure(startStates, intermediateStates, finalStates);

        List<Partition> partitions = fillPartition(grids);
        List<Cell> cells = fillCell(partitions, startStates, intermediateStates, finalStates);

        System.out.println(
                    transformRules.size() +
                    startStates.size() +
                    grids.size() +
                    cellAutomatons.size() +
                    sciences.size() +
                    scas.size() +
                    steps.size() +
                    finalStates.size() +
                    intermediateStates.size() +
                    structures.size() +
                    partitions.size() +
                    cells.size()
                );

    }

    private List<TransformRule> fillTransformRule(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<TransformRule> transformRules = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            transformRules.add(factory.manufacturePojo(TransformRule.class));

        transformRuleRepository.saveAll(transformRules);

        return transformRules;
    }

    private List<Cell> fillCell(List<Partition> partitions,
                                List<StartState> startStates,
                                List<IntermediateState> intermediateStates,
                                List<FinalState> finalStates) {
        PodamFactory factory = new PodamFactoryImpl();
        List<Cell> cells = new ArrayList<>();

        int[] lengths = {partitions.size(), startStates.size(), intermediateStates.size(), finalStates.size()};
        int count = Arrays.stream(lengths).max().getAsInt();
        NormalDistribution normalDistribution = new NormalDistribution(1, 0.3);

        for (int i = 0; i < count; i++) {
            double xCoord = (double) i/((double) count/2);
            long childCount = Math.round(normalDistribution.density(xCoord) * 100);
            for (int j = 0; j < childCount; j++) {
                Cell current = factory.manufacturePojo(Cell.class);

                current.setIntermediateState(intermediateStates.get(i));
                current.setStartState(startStates.get(i * startStates.size() / count));
                current.setFinalState(finalStates.get(i * finalStates.size() / count));
                current.setPartition(partitions.get(i * partitions.size() / count));

                cells.add(current);
            }
        }

        cellRepository.saveAll(cells);
        return cells;
    }

    private List<CellAutomaton> fillCellAutomaton(List<TransformRule> transformRules,
                                                  List<StartState> startStates,
                                                  List<Grid> grids) {
        PodamFactory factory = new PodamFactoryImpl();
        List<CellAutomaton> cellAutomatons = new ArrayList<>();

        int[] lengths = {startStates.size(), transformRules.size(), grids.size()};
        int count = Arrays.stream(lengths).max().getAsInt();
        NormalDistribution normalDistribution = new NormalDistribution(1, 0.3);

        for (int i = 0; i < count; i++) {
            double xCoord = (double) i/((double) count/2);
            long childCount = Math.round(normalDistribution.density(xCoord) * 100);
            for (int j = 0; j < childCount; j++) {
                CellAutomaton current = factory.manufacturePojo(CellAutomaton.class);

                current.setStartState(startStates.get(i));
                current.setTransformRule(transformRules.get(i * transformRules.size() / count));
                current.setGrid(grids.get(i * grids.size() / count));

                cellAutomatons.add(current);
            }
        }

        cellAutomatonRepository.saveAll(cellAutomatons);
        return cellAutomatons;
    }

    private List<FinalState> fillFinalState(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<FinalState> finalStates = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            finalStates.add(factory.manufacturePojo(FinalState.class));

        finalStateRepository.saveAll(finalStates);

        return finalStates;
    }

    private List<Grid> fillGrid(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<Grid> grids = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            grids.add(factory.manufacturePojo(Grid.class));

        gridRepository.saveAll(grids);

        return grids;
    }

    private List<IntermediateState> fillIntermediateState(List<StartState> startStates, List<FinalState> finalStates) {
        PodamFactory factory = new PodamFactoryImpl();
        List<IntermediateState> intermediateStates = new ArrayList<>();

        int[] lengths = {startStates.size(), finalStates.size()};
        int count = Arrays.stream(lengths).max().getAsInt();
        NormalDistribution normalDistribution = new NormalDistribution(1, 0.3);

        for (int i = 0; i < count; i++) {
            double xCoord = (double) i/((double) count/2);
            long childCount = Math.round(normalDistribution.density(xCoord) * 100);
            for (int j = 0; j < childCount; j++) {
                IntermediateState current = factory.manufacturePojo(IntermediateState.class);

                current.setStartState(startStates.get(i));
                current.setFinalState(finalStates.get(i * finalStates.size() / count));

                intermediateStates.add(current);
            }
        }

        intermediateStateRepository.saveAll(intermediateStates);
        return intermediateStates;
    }

    private List<Partition> fillPartition(List<Grid> grids) {
        PodamFactory factory = new PodamFactoryImpl();
        List<Partition> partitions = new ArrayList<>();

        int count = grids.size();
        NormalDistribution normalDistribution = new NormalDistribution(1, 0.3);

        for (int i = 0; i < count; i++) {
            double xCoord = (double) i/((double) count/2);
            long childCount = Math.round(normalDistribution.density(xCoord) * 100);
            for (int j = 0; j < childCount; j++) {
                Partition current = factory.manufacturePojo(Partition.class);

                current.setGrid(grids.get(i));

                partitions.add(current);
            }
        }

        partitionRepository.saveAll(partitions);
        return partitions;
    }

    private List<SCA> fillSca(List<Science> sciences, List<CellAutomaton> cellAutomatons) {
        PodamFactory factory = new PodamFactoryImpl();
        List<SCA> scas = new ArrayList<>();

        int[] lengths = {sciences.size(), cellAutomatons.size()};
        int count = Arrays.stream(lengths).max().getAsInt();
        NormalDistribution normalDistribution = new NormalDistribution(1, 0.3);

        for (int i = 0; i < count; i++) {
            double xCoord = (double) i/((double) count/2);
            long childCount = Math.round(normalDistribution.density(xCoord) * 100);
            for (int j = 0; j < childCount; j++) {
                SCA current = factory.manufacturePojo(SCA.class);

                current.setCellAutomaton(cellAutomatons.get(i));
                current.setScience(sciences.get(i * sciences.size() / count));

                scas.add(current);
            }
        }

        scaRepository.saveAll(scas);

        return scas;
    }

    private List<Science> fillScience(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<Science> sciences = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            sciences.add(factory.manufacturePojo(Science.class));

        scienceRepository.saveAll(sciences);

        return sciences;
    }

    private List<StartState> fillStartState(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<StartState> startStates = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            startStates.add(factory.manufacturePojo(StartState.class));

        startStateRepository.saveAll(startStates);

        return startStates;
    }

    private List<Step> fillStep(List<CellAutomaton> cellAutomatons) {
        PodamFactory factory = new PodamFactoryImpl();
        List<Step> steps = new ArrayList<>();

        int count = cellAutomatons.size();

        NormalDistribution normalDistribution = new NormalDistribution(1, 0.3);

        for (int i = 0; i < count; i++) {
            double xCoord = (double) i/((double) count/2);
            long childCount = Math.round(normalDistribution.density(xCoord) * 100);
            for (int j = 0; j < childCount; j++) {
                Step current = factory.manufacturePojo(Step.class);

                current.setCellAutomaton(cellAutomatons.get(i));

                steps.add(current);
            }
        }

        stepRepository.saveAll(steps);

        return steps;
    }

    private List<Structure> fillStructure(List<StartState> startStates,
                                          List<IntermediateState> intermediateStates,
                                          List<FinalState> finalStates) {
        PodamFactory factory = new PodamFactoryImpl();
        List<Structure> structures = new ArrayList<>();

        int[] lengths = {startStates.size(), intermediateStates.size(), finalStates.size()};
        int count = Arrays.stream(lengths).max().getAsInt();
        NormalDistribution normalDistribution = new NormalDistribution(1, 0.3);

        for (int i = 0; i < count; i++) {
            double xCoord = (double) i/((double) count/2);
            long childCount = Math.round(normalDistribution.density(xCoord) * 100);
            for (int j = 0; j < childCount; j++) {
                Structure current = factory.manufacturePojo(Structure.class);

                current.setIntermediateState(intermediateStates.get(i));
                current.setStartState(startStates.get(i * startStates.size() / count));
                current.setFinalState(finalStates.get(i * finalStates.size() / count));

                structures.add(current);
            }
        }

        structureRepository.saveAll(structures);
        return structures;
    }
    

//    private String generateRandomString() {
//        int length = 10;
//        boolean useLetters = true;
//        boolean useNumbers = true;
//        return RandomStringUtils.random(length, useLetters, useNumbers);
//    }
}
