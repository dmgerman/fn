begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer.fetcher
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fetcher
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|FieldContentParserPreferences
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|FetcherException
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|ImportFormatPreferences
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibLatexEntryTypes
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibtexEntryTypes
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|FieldName
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|OS
operator|.
name|NEWLINE
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertFalse
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|fail
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|mock
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|when
import|;
end_import

begin_class
DECL|class|AstrophysicsDataSystemTest
specifier|public
class|class
name|AstrophysicsDataSystemTest
block|{
DECL|field|fetcher
specifier|private
name|AstrophysicsDataSystem
name|fetcher
decl_stmt|;
DECL|field|diezSliceTheoremEntry
DECL|field|famaeyMcGaughEntry
DECL|field|sunWelchEntry
DECL|field|xiongSunEntry
DECL|field|ingersollPollardEntry
DECL|field|luceyPaulEntry
specifier|private
name|BibEntry
name|diezSliceTheoremEntry
decl_stmt|,
name|famaeyMcGaughEntry
decl_stmt|,
name|sunWelchEntry
decl_stmt|,
name|xiongSunEntry
decl_stmt|,
name|ingersollPollardEntry
decl_stmt|,
name|luceyPaulEntry
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|ImportFormatPreferences
name|importFormatPreferences
init|=
name|mock
argument_list|(
name|ImportFormatPreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|when
argument_list|(
name|importFormatPreferences
operator|.
name|getFieldContentParserPreferences
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|mock
argument_list|(
name|FieldContentParserPreferences
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
name|fetcher
operator|=
operator|new
name|AstrophysicsDataSystem
argument_list|(
name|importFormatPreferences
argument_list|)
expr_stmt|;
name|diezSliceTheoremEntry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|diezSliceTheoremEntry
operator|.
name|setType
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|diezSliceTheoremEntry
operator|.
name|setCiteKey
argument_list|(
literal|"2014arXiv1405.2249D"
argument_list|)
expr_stmt|;
name|diezSliceTheoremEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Diez, T."
argument_list|)
expr_stmt|;
name|diezSliceTheoremEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Slice theorem for Fr$\\backslash$'echet group actions and covariant symplectic field theory"
argument_list|)
expr_stmt|;
name|diezSliceTheoremEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2014"
argument_list|)
expr_stmt|;
name|diezSliceTheoremEntry
operator|.
name|setField
argument_list|(
literal|"archiveprefix"
argument_list|,
literal|"arXiv"
argument_list|)
expr_stmt|;
name|diezSliceTheoremEntry
operator|.
name|setField
argument_list|(
literal|"eprint"
argument_list|,
literal|"1405.2249"
argument_list|)
expr_stmt|;
name|diezSliceTheoremEntry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"ArXiv e-prints"
argument_list|)
expr_stmt|;
name|diezSliceTheoremEntry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Mathematical Physics, Mathematics - Differential Geometry, Mathematics - Symplectic Geometry, 58B99, 58Z05, 58B25, 22E65, 58D19, 53D20, 53D42"
argument_list|)
expr_stmt|;
name|diezSliceTheoremEntry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"#may#"
argument_list|)
expr_stmt|;
name|diezSliceTheoremEntry
operator|.
name|setField
argument_list|(
literal|"primaryclass"
argument_list|,
literal|"math-ph"
argument_list|)
expr_stmt|;
name|diezSliceTheoremEntry
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
literal|"A general slice theorem for the action of a Fr$\\backslash$'echet Lie group on a"
operator|+
name|NEWLINE
operator|+
literal|"Fr$\\backslash$'echet manifolds is established. The Nash-Moser theorem provides the"
operator|+
name|NEWLINE
operator|+
literal|"fundamental tool to generalize the result of Palais to this"
operator|+
name|NEWLINE
operator|+
literal|"infinite-dimensional setting. The presented slice theorem is illustrated"
operator|+
name|NEWLINE
operator|+
literal|"by its application to gauge theories: the action of the gauge"
operator|+
name|NEWLINE
operator|+
literal|"transformation group admits smooth slices at every point and thus the"
operator|+
name|NEWLINE
operator|+
literal|"gauge orbit space is stratified by Fr$\\backslash$'echet manifolds. Furthermore, a"
operator|+
name|NEWLINE
operator|+
literal|"covariant and symplectic formulation of classical field theory is"
operator|+
name|NEWLINE
operator|+
literal|"proposed and extensively discussed. At the root of this novel framework"
operator|+
name|NEWLINE
operator|+
literal|"is the incorporation of field degrees of freedom F and spacetime M into"
operator|+
name|NEWLINE
operator|+
literal|"the product manifold F * M. The induced bigrading of differential forms"
operator|+
name|NEWLINE
operator|+
literal|"is used in order to carry over the usual symplectic theory to this new"
operator|+
name|NEWLINE
operator|+
literal|"setting. The examples of the Klein-Gordon field and general Yang-Mills"
operator|+
name|NEWLINE
operator|+
literal|"theory illustrate that the presented approach conveniently handles the"
operator|+
name|NEWLINE
operator|+
literal|"occurring symmetries."
operator|+
name|NEWLINE
argument_list|)
expr_stmt|;
name|famaeyMcGaughEntry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|famaeyMcGaughEntry
operator|.
name|setType
argument_list|(
name|BibLatexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|famaeyMcGaughEntry
operator|.
name|setField
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"2012LRR....15...10F"
argument_list|)
expr_stmt|;
name|famaeyMcGaughEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Famaey, B. and McGaugh, S. S."
argument_list|)
expr_stmt|;
name|famaeyMcGaughEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Modified Newtonian Dynamics (MOND): Observational Phenomenology and Relativistic Extensions"
argument_list|)
expr_stmt|;
name|famaeyMcGaughEntry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"Living Reviews in Relativity"
argument_list|)
expr_stmt|;
name|famaeyMcGaughEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2012"
argument_list|)
expr_stmt|;
name|famaeyMcGaughEntry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"15"
argument_list|)
expr_stmt|;
name|famaeyMcGaughEntry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"#sep#"
argument_list|)
expr_stmt|;
name|famaeyMcGaughEntry
operator|.
name|setField
argument_list|(
literal|"archiveprefix"
argument_list|,
literal|"arXiv"
argument_list|)
expr_stmt|;
name|famaeyMcGaughEntry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.12942/lrr-2012-10"
argument_list|)
expr_stmt|;
name|famaeyMcGaughEntry
operator|.
name|setField
argument_list|(
literal|"eid"
argument_list|,
literal|"#10"
argument_list|)
expr_stmt|;
name|famaeyMcGaughEntry
operator|.
name|setField
argument_list|(
literal|"eprint"
argument_list|,
literal|"1112.3960"
argument_list|)
expr_stmt|;
name|famaeyMcGaughEntry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"#10"
argument_list|)
expr_stmt|;
name|famaeyMcGaughEntry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"astronomical observations, Newtonian limit, equations of motion, extragalactic astronomy, cosmology, theories of gravity, fundamental physics, astrophysics"
argument_list|)
expr_stmt|;
name|sunWelchEntry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|sunWelchEntry
operator|.
name|setType
argument_list|(
name|BibLatexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|sunWelchEntry
operator|.
name|setField
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"2012NatMa..11...44S"
argument_list|)
expr_stmt|;
name|sunWelchEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Sun, Y. and Welch, G. C. and Leong, W. L. and Takacs, C. J. and Bazan, G. C. and Heeger, A. J."
argument_list|)
expr_stmt|;
name|sunWelchEntry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.1038/nmat3160"
argument_list|)
expr_stmt|;
name|sunWelchEntry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"Nature Materials"
argument_list|)
expr_stmt|;
name|sunWelchEntry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"#jan#"
argument_list|)
expr_stmt|;
name|sunWelchEntry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"44-48"
argument_list|)
expr_stmt|;
name|sunWelchEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Solution-processed small-molecule solar cells with 6.7\\% efficiency"
argument_list|)
expr_stmt|;
name|sunWelchEntry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"11"
argument_list|)
expr_stmt|;
name|sunWelchEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2012"
argument_list|)
expr_stmt|;
name|xiongSunEntry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|xiongSunEntry
operator|.
name|setType
argument_list|(
name|BibLatexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|xiongSunEntry
operator|.
name|setField
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"2007ITGRS..45..879X"
argument_list|)
expr_stmt|;
name|xiongSunEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Xiong, X. and Sun, J. and Barnes, W. and Salomonson, V. and Esposito, J. and Erives, H. and Guenther, B."
argument_list|)
expr_stmt|;
name|xiongSunEntry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.1109/TGRS.2006.890567"
argument_list|)
expr_stmt|;
name|xiongSunEntry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"IEEE Transactions on Geoscience and Remote Sensing"
argument_list|)
expr_stmt|;
name|xiongSunEntry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"#apr#"
argument_list|)
expr_stmt|;
name|xiongSunEntry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"879-889"
argument_list|)
expr_stmt|;
name|xiongSunEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Multiyear On-Orbit Calibration and Performance of Terra MODIS Reflective Solar Bands"
argument_list|)
expr_stmt|;
name|xiongSunEntry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"45"
argument_list|)
expr_stmt|;
name|xiongSunEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2007"
argument_list|)
expr_stmt|;
name|ingersollPollardEntry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|ingersollPollardEntry
operator|.
name|setType
argument_list|(
name|BibLatexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|ingersollPollardEntry
operator|.
name|setField
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"1982Icar...52...62I"
argument_list|)
expr_stmt|;
name|ingersollPollardEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Ingersoll, A. P. and Pollard, D."
argument_list|)
expr_stmt|;
name|ingersollPollardEntry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.1016/0019-1035(82)90169-5"
argument_list|)
expr_stmt|;
name|ingersollPollardEntry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"\\icarus"
argument_list|)
expr_stmt|;
name|ingersollPollardEntry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Atmospheric Circulation, Barotropic Flow, Convective Flow, Flow Stability, Jupiter Atmosphere, Rotating Fluids, Saturn Atmosphere, Adiabatic Flow, Anelasticity, Compressible Fluids, Planetary Rotation, Rotating Cylinders, Scaling Laws, Wind Profiles, PLANETS, JUPITER, SATURN, MOTION, INTERIORS, ATMOSPHERE, ANALYSIS, SCALE, BAROTROPY, CHARACTERISTICS, STRUCTURE, WINDS, VISCOSITY, DATA, CONVECTION, ROTATION, EDDY EFFECTS, ENERGY, ADIABATICITY, DIAGRAMS, REVIEW, LATITUDE, ZONES, VELOCITY, MATHEMATICAL MODELS, HEAT FLOW, EQUATIONS OF MOTION, FLUIDS, DYNAMICS, TEMPERATURE, GRADIENTS"
argument_list|)
expr_stmt|;
name|ingersollPollardEntry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"#oct#"
argument_list|)
expr_stmt|;
name|ingersollPollardEntry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"62-80"
argument_list|)
expr_stmt|;
name|ingersollPollardEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Motion in the interiors and atmospheres of Jupiter and Saturn - Scale analysis, anelastic equations, barotropic stability criterion"
argument_list|)
expr_stmt|;
name|ingersollPollardEntry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"52"
argument_list|)
expr_stmt|;
name|ingersollPollardEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"1982"
argument_list|)
expr_stmt|;
name|luceyPaulEntry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|luceyPaulEntry
operator|.
name|setType
argument_list|(
name|BibLatexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|luceyPaulEntry
operator|.
name|setField
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"2000JGR...10520297L"
argument_list|)
expr_stmt|;
name|luceyPaulEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Lucey, P. G. and Blewett, D. T. and Jolliff, B. L."
argument_list|)
expr_stmt|;
name|luceyPaulEntry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.1029/1999JE001117"
argument_list|)
expr_stmt|;
name|luceyPaulEntry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"\\jgr"
argument_list|)
expr_stmt|;
name|luceyPaulEntry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Planetology: Solid Surface Planets: Composition, Planetology: Solid Surface Planets: Remote sensing, Planetology: Solid Surface Planets: Surface materials and properties, Planetology: Solar System Objects: Moon (1221)"
argument_list|)
expr_stmt|;
name|luceyPaulEntry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"20297-20306"
argument_list|)
expr_stmt|;
name|luceyPaulEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Lunar iron and titanium abundance algorithms based on final processing of Clementine ultraviolet-visible images"
argument_list|)
expr_stmt|;
name|luceyPaulEntry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"105"
argument_list|)
expr_stmt|;
name|luceyPaulEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2000"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testHelpPage ()
specifier|public
name|void
name|testHelpPage
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"ADS"
argument_list|,
name|fetcher
operator|.
name|getHelpPage
argument_list|()
operator|.
name|getPageName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetName ()
specifier|public
name|void
name|testGetName
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"SAO/NASA Astrophysics Data System"
argument_list|,
name|fetcher
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchByQueryFindsEntry ()
specifier|public
name|void
name|searchByQueryFindsEntry
parameter_list|()
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntries
init|=
name|fetcher
operator|.
name|performSearch
argument_list|(
literal|"Diez slice theorem"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|diezSliceTheoremEntry
argument_list|)
argument_list|,
name|fetchedEntries
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchByEntryFindsEntry ()
specifier|public
name|void
name|searchByEntryFindsEntry
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|searchEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|searchEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"slice theorem"
argument_list|)
expr_stmt|;
name|searchEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Diez"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntries
init|=
name|fetcher
operator|.
name|performSearch
argument_list|(
name|searchEntry
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|fetchedEntries
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|diezSliceTheoremEntry
argument_list|,
name|fetchedEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearchByFamaeyMcGaughEntry ()
specifier|public
name|void
name|testPerformSearchByFamaeyMcGaughEntry
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntry
init|=
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"10.12942/lrr-2012-10"
argument_list|)
decl_stmt|;
name|fetchedEntry
operator|.
name|ifPresent
argument_list|(
name|entry
lambda|->
name|entry
operator|.
name|clearField
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|)
argument_list|)
expr_stmt|;
comment|//Remove abstract due to copyright
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|famaeyMcGaughEntry
argument_list|)
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearchByIdEmptyDOI ()
specifier|public
name|void
name|testPerformSearchByIdEmptyDOI
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntry
init|=
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|FetcherException
operator|.
name|class
argument_list|)
DECL|method|testPerformSearchByIdInvalidDoi ()
specifier|public
name|void
name|testPerformSearchByIdInvalidDoi
parameter_list|()
throws|throws
name|Exception
block|{
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"this.doi.will.fail"
argument_list|)
expr_stmt|;
name|fail
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearchBySunWelchEntry ()
specifier|public
name|void
name|testPerformSearchBySunWelchEntry
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntry
init|=
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"10.1038/nmat3160"
argument_list|)
decl_stmt|;
name|fetchedEntry
operator|.
name|ifPresent
argument_list|(
name|entry
lambda|->
name|entry
operator|.
name|clearField
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|)
argument_list|)
expr_stmt|;
comment|//Remove abstract due to copyright
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|sunWelchEntry
argument_list|)
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearchByXiongSunEntry ()
specifier|public
name|void
name|testPerformSearchByXiongSunEntry
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntry
init|=
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"10.1109/TGRS.2006.890567"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|xiongSunEntry
argument_list|)
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearchByIngersollPollardEntry ()
specifier|public
name|void
name|testPerformSearchByIngersollPollardEntry
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntry
init|=
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"10.1016/0019-1035(82)90169-5"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|ingersollPollardEntry
argument_list|)
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearchByLuceyPaulEntry ()
specifier|public
name|void
name|testPerformSearchByLuceyPaulEntry
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntry
init|=
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"10.1029/1999JE001117"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|luceyPaulEntry
argument_list|)
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

