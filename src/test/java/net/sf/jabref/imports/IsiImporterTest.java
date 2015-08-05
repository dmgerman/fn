begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
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
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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

begin_comment
comment|/**  * Test cases for the IsiImporter  */
end_comment

begin_class
DECL|class|IsiImporterTest
specifier|public
class|class
name|IsiImporterTest
block|{
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
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|==
literal|null
condition|)
block|{
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testIsRecognizedFormat ()
specifier|public
name|void
name|testIsRecognizedFormat
parameter_list|()
throws|throws
name|IOException
block|{
name|IsiImporter
name|importer
init|=
operator|new
name|IsiImporter
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|IsiImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"IsiImporterTest1.isi"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|IsiImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"IsiImporterTestInspec.isi"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|IsiImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"IsiImporterTestWOS.isi"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|IsiImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"IsiImporterTestMedline.isi"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testProcessSubSup ()
specifier|public
name|void
name|testProcessSubSup
parameter_list|()
block|{
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|hm
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
literal|"/sub 3/"
argument_list|)
expr_stmt|;
name|IsiImporter
operator|.
name|processSubSup
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"$_3$"
argument_list|,
name|hm
operator|.
name|get
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
literal|"/sub   3   /"
argument_list|)
expr_stmt|;
name|IsiImporter
operator|.
name|processSubSup
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"$_3$"
argument_list|,
name|hm
operator|.
name|get
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
literal|"/sub 31/"
argument_list|)
expr_stmt|;
name|IsiImporter
operator|.
name|processSubSup
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"$_{31}$"
argument_list|,
name|hm
operator|.
name|get
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
literal|"/sub 3/"
argument_list|)
expr_stmt|;
name|IsiImporter
operator|.
name|processSubSup
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"$_3$"
argument_list|,
name|hm
operator|.
name|get
argument_list|(
literal|"abstract"
argument_list|)
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"review"
argument_list|,
literal|"/sub 31/"
argument_list|)
expr_stmt|;
name|IsiImporter
operator|.
name|processSubSup
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"$_{31}$"
argument_list|,
name|hm
operator|.
name|get
argument_list|(
literal|"review"
argument_list|)
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
literal|"/sup 3/"
argument_list|)
expr_stmt|;
name|IsiImporter
operator|.
name|processSubSup
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"$^3$"
argument_list|,
name|hm
operator|.
name|get
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
literal|"/sup 31/"
argument_list|)
expr_stmt|;
name|IsiImporter
operator|.
name|processSubSup
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"$^{31}$"
argument_list|,
name|hm
operator|.
name|get
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
literal|"/sup 3/"
argument_list|)
expr_stmt|;
name|IsiImporter
operator|.
name|processSubSup
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"$^3$"
argument_list|,
name|hm
operator|.
name|get
argument_list|(
literal|"abstract"
argument_list|)
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"review"
argument_list|,
literal|"/sup 31/"
argument_list|)
expr_stmt|;
name|IsiImporter
operator|.
name|processSubSup
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"$^{31}$"
argument_list|,
name|hm
operator|.
name|get
argument_list|(
literal|"review"
argument_list|)
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
literal|"/sub $Hello/"
argument_list|)
expr_stmt|;
name|IsiImporter
operator|.
name|processSubSup
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"$_{\\$Hello}$"
argument_list|,
name|hm
operator|.
name|get
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportEntries ()
specifier|public
name|void
name|testImportEntries
parameter_list|()
throws|throws
name|IOException
block|{
name|IsiImporter
name|importer
init|=
operator|new
name|IsiImporter
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|IsiImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"IsiImporterTest1.isi"
argument_list|)
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
name|entry
init|=
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Optical properties of MgO doped LiNbO$_3$ single crystals"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"James Brown and James Marc Brown and Brown, J. M. and Brown, J. and Brown, J. M. and Brown, J."
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|,
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Optical Materials"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"journal"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2006"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"28"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"volume"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"5"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"number"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"467--72"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
comment|// What todo with PD and UT?
block|}
annotation|@
name|Test
DECL|method|testImportEntriesINSPEC ()
specifier|public
name|void
name|testImportEntriesINSPEC
parameter_list|()
throws|throws
name|IOException
block|{
name|IsiImporter
name|importer
init|=
operator|new
name|IsiImporter
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|IsiImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"IsiImporterTestInspec.isi"
argument_list|)
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
name|a
init|=
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|BibtexEntry
name|b
init|=
name|entries
operator|.
name|get
argument_list|(
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|a
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"Optical and photoelectric spectroscopy of photorefractive Sn$_2$P$_2$S$_6$ crystals"
argument_list|)
condition|)
block|{
name|BibtexEntry
name|tmp
init|=
name|a
decl_stmt|;
name|a
operator|=
name|b
expr_stmt|;
name|b
operator|=
name|tmp
expr_stmt|;
block|}
comment|// Check a
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Second harmonic generation of continuous wave ultraviolet light and production of beta -BaB$_2$O$_4$ optical waveguides"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|,
name|a
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Degl'Innocenti, R. and Guarino, A. and Poberaj, G. and Gunter, P."
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Applied Physics Letters"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"journal"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2006"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"#jul#"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"month"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"89"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"volume"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"4"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"number"
argument_list|)
argument_list|)
expr_stmt|;
comment|// JI Appl. Phys. Lett. (USA)
comment|// BP 41103-1
comment|// EP 41103-41103-3
comment|// PS 41103-1-3
comment|// assertEquals("41103-1-3", a.getField("pages"));
comment|// LA English
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"We report on the generation of continuous-wave (cw) ultraviolet"
operator|+
literal|" (UV) laser light at lambda =278 nm by optical frequency doubling of"
operator|+
literal|" visible light in beta -BaB$_2$O$_4$ waveguides. Ridge-type "
operator|+
literal|"waveguides were produced by He$^+$ implantation, photolithography"
operator|+
literal|" masking, and plasma etching. The final waveguides have core dimension"
operator|+
literal|" of a few mu m$^2$ and show transmission losses of 5 dB/cm at 532 nm "
operator|+
literal|"and less than 10 dB/cm at 266 nm. In our first experiments, a second "
operator|+
literal|"harmonic power of 24 mu W has been generated at 278 nm in an 8 mm long "
operator|+
literal|"waveguide pumped by 153 mW at 556 nm."
operator|.
name|replaceFirst
argument_list|(
literal|"266"
argument_list|,
literal|"\n"
argument_list|)
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"abstract"
argument_list|)
argument_list|)
expr_stmt|;
comment|/*          * DE Experimental/ barium compounds; ion implantation; optical harmonic          * generation; optical losses; optical pumping; photolithography; solid          * lasers; sputter etching; ultraviolet sources; waveguide lasers/          * second harmonic generation; continuous-wave light; beta -BaB/sub          * 2/O/sub 4/ optical waveguides; UV laser light; optical frequency          * doubling; visible light; ridge-type waveguides; He/sup +/          * implantation; photolithography masking; plasma etching; transmission          * losses; optical pumping; 278 nm; 532 nm; 266 nm; 24 muW; 8 mm; 153          * mW; 556 nm; BaB/sub 2/O/sub 4// A4265K Optical harmonic generation,          * frequency conversion, parametric oscillation and amplification A4255R          * Lasing action in other solids A4260B Design of specific laser systems          * B4340K Optical harmonic generation, frequency conversion, parametric          * oscillation and amplification B4320G Solid lasers/ wavelength          * 2.78E-07 m; wavelength 5.32E-07 m; wavelength 2.66E-07 m; power          * 2.4E-05 W; size 8.0E-03 m; power 1.53E-01 W; wavelength 5.56E-07 m/          * BaB2O4/ss B2/ss Ba/ss O4/ss B/ss O/ss C1 Degl'Innocenti, R.; Guarino,          * A.; Poberaj, G.; Gunter, P.; Nonlinear Opt. Lab., Inst. of Quantum          * Electron., Zurich, Switzerland          */
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Aip"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"publisher"
argument_list|)
argument_list|)
expr_stmt|;
comment|// PV USA
comment|// NR 11
comment|// CO APPLAB
comment|// SN 0003-6951
comment|// ID
comment|// 0003-6951/2006/89(4)/041103-1(3)/$23.00],[0003-6951(20060724)89:4L.41103:SHGC;1-T],[S0003-6951(06)22430-6],[10.1063/1.2234275]
comment|// UT INSPEC:9027814
comment|// Check B
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Optical and photoelectric spectroscopy of photorefractive Sn$_2$P$_2$S$_6$ crystals"
argument_list|,
name|b
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|,
name|b
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportEntriesWOS ()
specifier|public
name|void
name|testImportEntriesWOS
parameter_list|()
throws|throws
name|IOException
block|{
name|IsiImporter
name|importer
init|=
operator|new
name|IsiImporter
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|IsiImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"IsiImporterTestWOS.isi"
argument_list|)
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
name|a
init|=
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|BibtexEntry
name|b
init|=
name|entries
operator|.
name|get
argument_list|(
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|a
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"Optical waveguides in Sn2P2S6 by low fluence MeV He+ ion implantation"
argument_list|)
condition|)
block|{
name|BibtexEntry
name|tmp
init|=
name|a
decl_stmt|;
name|a
operator|=
name|b
expr_stmt|;
name|b
operator|=
name|tmp
expr_stmt|;
block|}
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Optical and photoelectric spectroscopy of photorefractive Sn2P2S6 crystals"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Optical waveguides in Sn2P2S6 by low fluence MeV He+ ion implantation"
argument_list|,
name|b
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Journal of Physics-condensed Matter"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"journal"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsiAuthorsConvert ()
specifier|public
name|void
name|testIsiAuthorsConvert
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"James Brown and James Marc Brown and Brown, J. M. and Brown, J. and Brown, J. M. and Brown, J."
argument_list|,
name|IsiImporter
operator|.
name|isiAuthorsConvert
argument_list|(
literal|"James Brown and James Marc Brown and Brown, J.M. and Brown, J. and Brown, J.M. and Brown, J."
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Joffe, Hadine and Hall, Janet E. and Gruber, Staci and Sarmiento, Ingrid A. and Cohen, Lee S. and Yurgelun-Todd, Deborah and Martin, Kathryn A."
argument_list|,
name|IsiImporter
operator|.
name|isiAuthorsConvert
argument_list|(
literal|"Joffe, Hadine; Hall, Janet E; Gruber, Staci; Sarmiento, Ingrid A; Cohen, Lee S; Yurgelun-Todd, Deborah; Martin, Kathryn A"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testMonthConvert ()
specifier|public
name|void
name|testMonthConvert
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"#jun#"
argument_list|,
name|IsiImporter
operator|.
name|parseMonth
argument_list|(
literal|"06"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"#jun#"
argument_list|,
name|IsiImporter
operator|.
name|parseMonth
argument_list|(
literal|"JUN"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"#jun#"
argument_list|,
name|IsiImporter
operator|.
name|parseMonth
argument_list|(
literal|"jUn"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"#may#"
argument_list|,
name|IsiImporter
operator|.
name|parseMonth
argument_list|(
literal|"MAY-JUN"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"#jun#"
argument_list|,
name|IsiImporter
operator|.
name|parseMonth
argument_list|(
literal|"2006 06"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"#jun#"
argument_list|,
name|IsiImporter
operator|.
name|parseMonth
argument_list|(
literal|"2006 06-07"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"#jul#"
argument_list|,
name|IsiImporter
operator|.
name|parseMonth
argument_list|(
literal|"2006 07 03"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"#may#"
argument_list|,
name|IsiImporter
operator|.
name|parseMonth
argument_list|(
literal|"2006 May-Jun"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsiAuthorConvert ()
specifier|public
name|void
name|testIsiAuthorConvert
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"James Brown"
argument_list|,
name|IsiImporter
operator|.
name|isiAuthorConvert
argument_list|(
literal|"James Brown"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"James Marc Brown"
argument_list|,
name|IsiImporter
operator|.
name|isiAuthorConvert
argument_list|(
literal|"James Marc Brown"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Brown, J. M."
argument_list|,
name|IsiImporter
operator|.
name|isiAuthorConvert
argument_list|(
literal|"Brown, J.M."
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Brown, J."
argument_list|,
name|IsiImporter
operator|.
name|isiAuthorConvert
argument_list|(
literal|"Brown, J."
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Brown, J. M."
argument_list|,
name|IsiImporter
operator|.
name|isiAuthorConvert
argument_list|(
literal|"Brown, JM"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Brown, J."
argument_list|,
name|IsiImporter
operator|.
name|isiAuthorConvert
argument_list|(
literal|"Brown, J"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Brown, James"
argument_list|,
name|IsiImporter
operator|.
name|isiAuthorConvert
argument_list|(
literal|"Brown, James"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Hall, Janet E."
argument_list|,
name|IsiImporter
operator|.
name|isiAuthorConvert
argument_list|(
literal|"Hall, Janet E"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|IsiImporter
operator|.
name|isiAuthorConvert
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetIsCustomImporter ()
specifier|public
name|void
name|testGetIsCustomImporter
parameter_list|()
block|{
name|IsiImporter
name|importer
init|=
operator|new
name|IsiImporter
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|false
argument_list|,
name|importer
operator|.
name|getIsCustomImporter
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportIEEEExport ()
specifier|public
name|void
name|testImportIEEEExport
parameter_list|()
throws|throws
name|IOException
block|{
name|IsiImporter
name|importer
init|=
operator|new
name|IsiImporter
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|IsiImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"IEEEImport1.txt"
argument_list|)
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
name|a
init|=
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|a
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|,
name|a
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Geoscience and Remote Sensing Letters, IEEE"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"journal"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Improving Urban Road Extraction in High-Resolution "
operator|+
literal|"Images Exploiting Directional Filtering, Perceptual "
operator|+
literal|"Grouping, and Simple Topological Concepts"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"4"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"volume"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"3"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"number"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"1545-598X"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"SN"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"387--391"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Gamba, P. and Dell'Acqua, F. and Lisini, G."
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2006"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Perceptual grouping, street extraction, urban remote sensing"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"keywords"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"In this letter, the problem of detecting urban road "
operator|+
literal|"networks from high-resolution optical/synthetic aperture "
operator|+
literal|"radar (SAR) images is addressed. To this end, this letter "
operator|+
literal|"exploits a priori knowledge about road direction "
operator|+
literal|"distribution in urban areas. In particular, this letter "
operator|+
literal|"presents an adaptive filtering procedure able to capture the "
operator|+
literal|"predominant directions of these roads and enhance the "
operator|+
literal|"extraction results. After road element extraction, to both "
operator|+
literal|"discard redundant segments and avoid gaps, a special "
operator|+
literal|"perceptual grouping algorithm is devised, exploiting "
operator|+
literal|"colinearity as well as proximity concepts. Finally, the road "
operator|+
literal|"network topology is considered, checking for road "
operator|+
literal|"intersections and regularizing the overall patterns using "
operator|+
literal|"these focal points. The proposed procedure was tested on a "
operator|+
literal|"pair of very high resolution images, one from an optical "
operator|+
literal|"sensor and one from a SAR sensor. The experiments show an "
operator|+
literal|"increase in both the completeness and the quality indexes "
operator|+
literal|"for the extracted road network."
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"abstract"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportEntriesMedline ()
specifier|public
name|void
name|testImportEntriesMedline
parameter_list|()
throws|throws
name|IOException
block|{
name|IsiImporter
name|importer
init|=
operator|new
name|IsiImporter
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|IsiImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"IsiImporterTestMedline.isi"
argument_list|)
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
name|a
init|=
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|BibtexEntry
name|b
init|=
name|entries
operator|.
name|get
argument_list|(
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|a
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
operator|)
operator|.
name|startsWith
argument_list|(
literal|"Estrogen"
argument_list|)
condition|)
block|{
name|BibtexEntry
name|tmp
init|=
name|a
decl_stmt|;
name|a
operator|=
name|b
expr_stmt|;
name|b
operator|=
name|tmp
expr_stmt|;
block|}
comment|// Check A
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Effects of modafinil on cognitive performance and alertness during sleep deprivation."
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Wesensten, Nancy J."
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Curr Pharm Des"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"journal"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2006"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"month"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"12"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"volume"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"20"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"number"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2457--71"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|,
name|a
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
comment|// Check B
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Estrogen therapy selectively enhances prefrontal cognitive processes: a randomized, double-blind, placebo-controlled study with functional magnetic resonance imaging in perimenopausal and recently postmenopausal women."
argument_list|,
name|b
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Joffe, Hadine and Hall, Janet E. and Gruber, Staci and Sarmiento, Ingrid A. and Cohen, Lee S. and Yurgelun-Todd, Deborah and Martin, Kathryn A."
argument_list|,
name|b
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2006"
argument_list|,
name|b
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"#may#"
argument_list|,
name|b
operator|.
name|getField
argument_list|(
literal|"month"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"13"
argument_list|,
name|b
operator|.
name|getField
argument_list|(
literal|"volume"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"3"
argument_list|,
name|b
operator|.
name|getField
argument_list|(
literal|"number"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"411--22"
argument_list|,
name|b
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|,
name|b
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

