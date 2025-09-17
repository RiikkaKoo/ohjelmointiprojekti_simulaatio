package simu.model;

import simu.framework.ITapahtumanTyyppi;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum TapahtumanTyyppi implements ITapahtumanTyyppi{
	ARR1, ARR2, ARR3, FIN1, FIN2, FIN3, FIN4, DEP1, DEP2, DEP3, DEP4;

}


/* TAPAHTUMAT:

ARR1: Asiakas saapuu autokaistalle
ARR2: Asiakas saapuu palvelutiskille (sisätiloissa)
ARR3: Asiakas saapuu tilausautomaatille

FIN1: Tilaus asetettu autokaistalla
FIN2: Tilaus asetettu palvelustiskillä
FIN3: Tilaus asetettu tilausautomaatilla

FIN4: Tilaus valmistettu keittiössä

DEP1: Tilaustoimitettu autokaista-asiakkaalle - asiakas poistuu systeemistä
DEP2: Tilaus noudettu noutotiskiltä - (To-go -asiakas poistuu systeemistä)

DEP3: Asiakas on syönyt ateriansa - asiakas poistuu systeemistä
DEP4: Asiakas on uudelleenpakkauttanut ateriansa - asiakas poistuu systeemistä
 */
