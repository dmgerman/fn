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
name|FieldName
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
name|testutils
operator|.
name|category
operator|.
name|FetcherTests
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
name|org
operator|.
name|junit
operator|.
name|experimental
operator|.
name|categories
operator|.
name|Category
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
name|assertTrue
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

begin_class
annotation|@
name|Category
argument_list|(
name|FetcherTests
operator|.
name|class
argument_list|)
DECL|class|MedlineFetcherTest
specifier|public
class|class
name|MedlineFetcherTest
block|{
DECL|field|fetcher
specifier|private
name|MedlineFetcher
name|fetcher
decl_stmt|;
DECL|field|entryWijedasa
DECL|field|entryEndharti
DECL|field|bibEntryIchikawa
DECL|field|bibEntrySari
specifier|private
name|BibEntry
name|entryWijedasa
decl_stmt|,
name|entryEndharti
decl_stmt|,
name|bibEntryIchikawa
decl_stmt|,
name|bibEntrySari
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|fetcher
operator|=
operator|new
name|MedlineFetcher
argument_list|()
expr_stmt|;
name|entryWijedasa
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|entryWijedasa
operator|.
name|setType
argument_list|(
name|BibLatexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Wijedasa, Lahiru S and Jauhiainen, Jyrki and ÃnÃ¶nen, Mari K and Lampela, Maija and Vasander, Harri and Leblanc, Marie-Claire and Evers, Stephanie and Smith, Thomas E L and Yule, Catherine M and Varkkey, Helena and Lupascu, Massimo and Parish, Faizal and Singleton, Ian and Clements, Gopalasamy R and Aziz, Sheema Abdul and Harrison, Mark E and Cheyne, Susan and Anshari, Gusti Z and Meijaard, Erik and Goldstein, Jenny E and Waldron, Susan and Hergoualc'h, Kristell and Dommain, Rene and Frolking, Steve and Evans, Christopher D and Posa, Mary Rose C and Glaser, Paul H and Suryadiputra, Nyoman and Lubis, Reza and Santika, Truly and Padfield, Rory and Kurnianto, Sofyan and Hadisiswoyo, Panut and Lim, Teck Wyn and Page, Susan E and Gauci, Vincent and Van Der Meer, Peter J and Buckland, Helen and Garnier, Fabien and Samuel, Marshall K and Choo, Liza Nuriati Lim Kim and O'Reilly, Patrick and Warren, Matthew and Suksuwan, Surin and Sumarga, Elham and Jain, Anuj and Laurance, William F and Couwenberg, John and Joosten, Hans and Vernimmen, Ronald and Hooijer, Aljosja and Malins, Chris and Cochrane, Mark A and Perumal, Balu and Siegert, Florian and Peh, Kelvin S-H and Comeau, Louis-Pierre and Verchot, Louis and Harvey, Charles F and Cobb, Alex and Jaafar, Zeehan and WÃ¶sten, Henk and Manuri, Solichin and MÃ¼ller, Moritz and Giesen, Wim and Phelps, Jacob and Yong, Ding Li and Silvius, Marcel and Wedeux, BÃ©atrice M M and Hoyt, Alison and Osaki, Mitsuru and Hirano, Takashi and Takahashi, Hidenori and Kohyama, Takashi S and Haraguchi, Akira and Nugroho, Nunung P and Coomes, David A and Quoi, Le Phat and Dohong, Alue and Gunawan, Haris and Gaveau, David L A and Langner, Andreas and Lim, Felix K S and Edwards, David P and Giam, Xingli and Van Der Werf, Guido and Carmenta, Rachel and Verwer, Caspar C and Gibson, Luke and Gandois, Laure and Graham, Laura Linda Bozena and Regalino, Jhanson and Wich, Serge A and Rieley, Jack and Kettridge, Nicholas and Brown, Chloe and Pirard, Romain and Moore, Sam and Capilla, B Ripoll and Ballhorn, Uwe and Ho, Hua Chew and Hoscilo, Agata and Lohberger, Sandra and Evans, Theodore A and Yulianti, Nina and Blackham, Grace and Onrizal and Husson, Simon and Murdiyarso, Daniel and Pangala, Sunita and Cole, Lydia E S and Tacconi, Luca and Segah, Hendrik and Tonoto, Prayoto and Lee, Janice S H and Schmilewski, Gerald and Wulffraat, Stephan and Putra, Erianto Indra and Cattau, Megan E and Clymo, R S and Morrison, Ross and Mujahid, Aazani and Miettinen, Jukka and Liew, Soo Chin and Valpola, Samu and Wilson, David and D'Arcy, Laura and Gerding, Michiel and Sundari, Siti and Thornton, Sara A and Kalisz, Barbara and Chapman, Stephen J and Su, Ahmad Suhaizi Mat and Basuki, Imam and Itoh, Masayuki and Traeholt, Carl and Sloan, Sean and Sayok, Alexander K and Andersen, Roxane"
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"created"
argument_list|,
literal|"2016-09-27"
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"country"
argument_list|,
literal|"England"
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.1111/gcb.13516"
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"issn"
argument_list|,
literal|"1365-2486"
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"issn-linking"
argument_list|,
literal|"1354-1013"
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"Global change biology"
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"#sep#"
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"nlm-id"
argument_list|,
literal|"9888746"
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"owner"
argument_list|,
literal|"NLM"
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"pmid"
argument_list|,
literal|"27670948"
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"pubmodel"
argument_list|,
literal|"Print-Electronic"
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"pubstatus"
argument_list|,
literal|"aheadofprint"
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"revised"
argument_list|,
literal|"2017-01-10"
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Denial of long-term issues with agriculture on tropical peatlands will have devastating consequences."
argument_list|)
expr_stmt|;
name|entryWijedasa
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|entryEndharti
operator|.
name|setType
argument_list|(
name|BibLatexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Dendrophthoe pentandra (L.) Miq extract effectively inhibits inflammation, proliferation and induces p53 expression on colitis-associated colon cancer."
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Endharti, Agustina Tri and Wulandari, Adisti and Listyana, Anik and Norahmawati, Eviana and Permana, Sofy"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"created"
argument_list|,
literal|"2016-09-27"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"country"
argument_list|,
literal|"England"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.1186/s12906-016-1345-0"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"pii"
argument_list|,
literal|"10.1186/s12906-016-1345-0"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"pmc"
argument_list|,
literal|"PMC5037598"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"issn"
argument_list|,
literal|"1472-6882"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"issn-linking"
argument_list|,
literal|"1472-6882"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"issue"
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"BMC complementary and alternative medicine"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"CAC; Dendrophtoe pentandra; IL-22; MPO; Proliferation; p53"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"nlm-id"
argument_list|,
literal|"101088661"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"owner"
argument_list|,
literal|"NLM"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"374"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"#sep#"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"pmid"
argument_list|,
literal|"27670445"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"pubmodel"
argument_list|,
literal|"Electronic"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"pubstatus"
argument_list|,
literal|"epublish"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"revised"
argument_list|,
literal|"2016-10-11"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"16"
argument_list|)
expr_stmt|;
name|entryEndharti
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setType
argument_list|(
name|BibLatexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Ichikawa-Seki, Madoka and Guswanto, Azirwan and Allamanda, Puttik and Mariamah, Euis Siti and Wibowo, Putut Eko and Igarashi, Ikuo and Nishikawa, Yoshifumi"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"chemicals"
argument_list|,
literal|"Antibodies, Protozoan, Antigens, Protozoan, GRA7 protein, Toxoplasma gondii, Protozoan Proteins"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"citation-subset"
argument_list|,
literal|"IM"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"completed"
argument_list|,
literal|"2016-07-26"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"country"
argument_list|,
literal|"Netherlands"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"created"
argument_list|,
literal|"2015-09-26"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.1016/j.parint.2015.07.004"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"issn"
argument_list|,
literal|"1873-0329"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"pubstatus"
argument_list|,
literal|"ppublish"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"revised"
argument_list|,
literal|"2015-09-26"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"issn-linking"
argument_list|,
literal|"1383-5769"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"issue"
argument_list|,
literal|"6"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"Parasitology international"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Animals; Antibodies, Protozoan, blood; Antigens, Protozoan, immunology; Cattle, parasitology; Cattle Diseases, epidemiology, parasitology; Enzyme-Linked Immunosorbent Assay, veterinary; Geography; Humans; Indonesia, epidemiology; Livestock, immunology, parasitology; Meat, parasitology; Protozoan Proteins, immunology; Seroepidemiologic Studies; Swine, parasitology; Swine Diseases, epidemiology, parasitology; Toxoplasma, immunology; Toxoplasmosis, Animal, epidemiology, immunology, parasitology; Cattle; ELISA; Indonesia; Pig; TgGRA7; Toxoplasma gondii"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"#dec#"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"nlm-id"
argument_list|,
literal|"9708549"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"owner"
argument_list|,
literal|"NLM"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"484--486"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"pii"
argument_list|,
literal|"S1383-5769(15)00124-5"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"pmid"
argument_list|,
literal|"26197440"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"pubmodel"
argument_list|,
literal|"Print-Electronic"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Seroprevalence of antibody to TgGRA7 antigen of Toxoplasma gondii in livestock animals from Western Java, Indonesia."
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"64"
argument_list|)
expr_stmt|;
name|bibEntryIchikawa
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2015"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|bibEntrySari
operator|.
name|setType
argument_list|(
name|BibLatexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Sari, Yulia and Haryati, Sri and Raharjo, Irvan and Prasetyo, Afiono Agung"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"chemicals"
argument_list|,
literal|"Antibodies, Protozoan, Antibodies, Viral, HTLV-I Antibodies, HTLV-II Antibodies, Hepatitis Antibodies, Hepatitis B Antibodies, Hepatitis C Antibodies, Immunoglobulin G, Immunoglobulin M"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"citation-subset"
argument_list|,
literal|"IM"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"completed"
argument_list|,
literal|"2016-04-21"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"country"
argument_list|,
literal|"Thailand"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"created"
argument_list|,
literal|"2016-02-12"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"issn"
argument_list|,
literal|"0125-1562"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"issn-linking"
argument_list|,
literal|"0125-1562"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"issue"
argument_list|,
literal|"6"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"The Southeast Asian journal of tropical medicine and public health"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Antibodies, Protozoan; Antibodies, Viral, immunology; Coinfection, epidemiology, immunology; Female; HIV Infections, epidemiology; HTLV-I Antibodies, immunology; HTLV-I Infections, epidemiology, immunology; HTLV-II Antibodies, immunology; HTLV-II Infections, epidemiology, immunology; Hepatitis Antibodies, immunology; Hepatitis B Antibodies, immunology; Hepatitis C Antibodies, immunology; Hepatitis Delta Virus, immunology; Hepatitis, Viral, Human, epidemiology, immunology; Humans; Immunoglobulin G, immunology; Immunoglobulin M, immunology; Indonesia, epidemiology; Male; Prisoners; Seroepidemiologic Studies; Toxoplasma, immunology; Toxoplasmosis, epidemiology, immunology"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"#nov#"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"pubstatus"
argument_list|,
literal|"ppublish"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"revised"
argument_list|,
literal|"2016-02-12"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"nlm-id"
argument_list|,
literal|"0266303"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"owner"
argument_list|,
literal|"NLM"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"977--985"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"pmid"
argument_list|,
literal|"26867355"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"pubmodel"
argument_list|,
literal|"Print"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"TOXOPLASMA AND VIRAL ANTIBODIES AMONG HIV PATIENTS AND INMATES IN CENTRAL JAVA, INDONESIA."
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"46"
argument_list|)
expr_stmt|;
name|bibEntrySari
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2015"
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
literal|"Medline"
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
DECL|method|testGetHelpPage ()
specifier|public
name|void
name|testGetHelpPage
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Medline"
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
DECL|method|testSearchByIDWijedasa ()
specifier|public
name|void
name|testSearchByIDWijedasa
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
literal|"27670948"
argument_list|)
decl_stmt|;
name|fetchedEntry
operator|.
name|get
argument_list|()
operator|.
name|clearField
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|)
expr_stmt|;
comment|//Remove abstract due to copyright
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|entryWijedasa
argument_list|)
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSearchByIDEndharti ()
specifier|public
name|void
name|testSearchByIDEndharti
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
literal|"27670445"
argument_list|)
decl_stmt|;
name|fetchedEntry
operator|.
name|get
argument_list|()
operator|.
name|clearField
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|)
expr_stmt|;
comment|//Remove abstract due to copyright
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|entryEndharti
argument_list|)
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSearchByIDIchikawa ()
specifier|public
name|void
name|testSearchByIDIchikawa
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
literal|"26197440"
argument_list|)
decl_stmt|;
name|fetchedEntry
operator|.
name|get
argument_list|()
operator|.
name|clearField
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|)
expr_stmt|;
comment|//Remove abstract due to copyright
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|bibEntryIchikawa
argument_list|)
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSearchByIDSari ()
specifier|public
name|void
name|testSearchByIDSari
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
literal|"26867355"
argument_list|)
decl_stmt|;
name|fetchedEntry
operator|.
name|get
argument_list|()
operator|.
name|clearField
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|)
expr_stmt|;
comment|//Remove abstract due to copyright
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|bibEntrySari
argument_list|)
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testMultipleEntries ()
specifier|public
name|void
name|testMultipleEntries
parameter_list|()
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entryList
init|=
name|fetcher
operator|.
name|performSearch
argument_list|(
literal|"java"
argument_list|)
decl_stmt|;
name|entryList
operator|.
name|forEach
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
comment|//Remove abstract due to copyright);
name|assertEquals
argument_list|(
literal|50
argument_list|,
name|entryList
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|entryList
operator|.
name|contains
argument_list|(
name|bibEntryIchikawa
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|entryList
operator|.
name|contains
argument_list|(
name|bibEntrySari
argument_list|)
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
comment|//caused by Optional.of(entry.get(0))
DECL|method|testInvalidSearchTermCauseIndexOutOfBoundsException ()
specifier|public
name|void
name|testInvalidSearchTermCauseIndexOutOfBoundsException
parameter_list|()
throws|throws
name|Exception
block|{
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"this.is.a.invalid.search.term.for.the.medline.fetcher"
argument_list|)
expr_stmt|;
name|fail
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEmptyEntryList ()
specifier|public
name|void
name|testEmptyEntryList
parameter_list|()
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entryList
init|=
name|fetcher
operator|.
name|performSearch
argument_list|(
literal|"java is fantastic and awesome "
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|entryList
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEmptyInput ()
specifier|public
name|void
name|testEmptyInput
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|fetcher
operator|.
name|performSearch
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

