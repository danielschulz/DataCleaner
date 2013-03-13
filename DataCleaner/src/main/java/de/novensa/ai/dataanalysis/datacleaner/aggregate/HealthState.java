package de.novensa.ai.dataanalysis.datacleaner.aggregate;

/**
 * Helps to determine whether a patient is healthy, diagnosed Parkinson, or predicted having PD. We assume diagnosed PD
 * as a nearly surefire sign the patient is sick of Parkinson's disease. A patient being predicted having PD is far
 * less likely really having PD. You can use this enumeration to establish even more levels of safeness on this
 * information.
 *
 * @author Daniel Schulz
 */
@SuppressWarnings("UnusedDeclaration")
public enum HealthState {
    healthy, predictedPD, diagnosedPD
}
