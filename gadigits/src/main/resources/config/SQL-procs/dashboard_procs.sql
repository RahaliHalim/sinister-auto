-- Function: public.count_bons_sortie(date, date)

-- DROP FUNCTION public.count_bons_sortie(date, date);

CREATE OR REPLACE FUNCTION public.count_bons_sortie(
    d_debut date,
    d_fin date)
  RETURNS integer AS
$BODY$
declare
	nbr integer;
begin
  
select count(distinct dossier_sinistre) into nbr from facture where bon_sortie is not null
and  (date_creation between d_debut and d_fin ) ;
return nbr ;

end$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.count_bons_sortie(date, date)
  OWNER TO postgres;

-- Function: public.count_dossiers(date, date)

-- DROP FUNCTION public.count_dossiers(date, date);

CREATE OR REPLACE FUNCTION public.count_dossiers(
    IN d_debut date,
    IN d_fin date,
    OUT nbr integer)
  RETURNS integer AS
$BODY$begin
  
select count(*) into nbr from dossier_sinistre_auto where date_creation between d_debut and d_fin;

end$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.count_dossiers(date, date)
  OWNER TO postgres;
-- Function: public.delai_immobilisation(date, date)

-- DROP FUNCTION public.delai_immobilisation(date, date);

CREATE OR REPLACE FUNCTION public.delai_immobilisation(
    d_debut date,
    d_fin date)
  RETURNS double precision AS
$BODY$
declare
	moyenne interval;
	moy double precision;
begin
  
select AVG(b.date_edition - d.date_creation) into moyenne
from bon_sortie b  join facture f on b.id_bon_sortie = f.bon_sortie join dossier_sinistre_auto d 
on f.dossier_sinistre= d.id_dossier_sinistre_auto 
 where (b.date_edition between d_debut and d_fin);

select EXTRACT (epoch from moyenne)/86400 into moy;
return moy;
end;
 $BODY$
  LANGUAGE plpgsql STABLE
  COST 100;
ALTER FUNCTION public.delai_immobilisation(date, date)
  OWNER TO postgres;

