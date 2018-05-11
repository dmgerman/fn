begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry.identifier
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|identifier
package|;
end_package

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
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
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
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertThrows
import|;
end_import

begin_class
DECL|class|DOITest
specifier|public
class|class
name|DOITest
block|{
annotation|@
name|Test
DECL|method|acceptPlainDoi ()
specifier|public
name|void
name|acceptPlainDoi
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"10.1006/jmbi.1998.2354"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"10.1006/jmbi.1998.2354"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.231/JIM.0b013e31820bab4c"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"10.231/JIM.0b013e31820bab4c"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1002/(SICI)1522-2594(199911)42:5<952::AID-MRM16>3.0.CO;2-S"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"10.1002/(SICI)1522-2594(199911)42:5<952::AID-MRM16>3.0.CO;2-S"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1126/sciadv.1500214"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"10.1126/sciadv.1500214"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|ignoreLeadingAndTrailingWhitespaces ()
specifier|public
name|void
name|ignoreLeadingAndTrailingWhitespaces
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"10.1006/jmbi.1998.2354"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"  10.1006/jmbi.1998.2354 "
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|rejectEmbeddedDoi ()
specifier|public
name|void
name|rejectEmbeddedDoi
parameter_list|()
block|{
name|assertThrows
argument_list|(
name|IllegalArgumentException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
operator|new
name|DOI
argument_list|(
literal|"other stuff 10.1006/jmbi.1998.2354 end"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|rejectInvalidDirectoryIndicator ()
specifier|public
name|void
name|rejectInvalidDirectoryIndicator
parameter_list|()
block|{
comment|// wrong directory indicator
name|assertThrows
argument_list|(
name|IllegalArgumentException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
operator|new
name|DOI
argument_list|(
literal|"12.1006/jmbi.1998.2354 end"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|rejectInvalidDoiUri ()
specifier|public
name|void
name|rejectInvalidDoiUri
parameter_list|()
block|{
name|assertThrows
argument_list|(
name|IllegalArgumentException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
operator|new
name|DOI
argument_list|(
literal|"https://thisisnouri"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|rejectMissingDivider ()
specifier|public
name|void
name|rejectMissingDivider
parameter_list|()
block|{
comment|// missing divider
name|assertThrows
argument_list|(
name|IllegalArgumentException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
operator|new
name|DOI
argument_list|(
literal|"10.1006jmbi.1998.2354 end"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|acceptDoiPrefix ()
specifier|public
name|void
name|acceptDoiPrefix
parameter_list|()
block|{
comment|// Doi prefix
name|assertEquals
argument_list|(
literal|"10.1006/jmbi.1998.2354"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"doi:10.1006/jmbi.1998.2354"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|acceptURNPrefix ()
specifier|public
name|void
name|acceptURNPrefix
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"10.123/456"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"urn:10.123/456"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.123/456"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"urn:doi:10.123/456"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.123/456"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://doi.org/urn:doi:10.123/456"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
comment|// : is also allowed as divider, will be replaced by RESOLVER
name|assertEquals
argument_list|(
literal|"10.123:456ABC/zyz"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://doi.org/urn:doi:10.123:456ABC%2Fzyz"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|acceptURLDoi ()
specifier|public
name|void
name|acceptURLDoi
parameter_list|()
block|{
comment|// http
name|assertEquals
argument_list|(
literal|"10.1006/jmbi.1998.2354"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://doi.org/10.1006/jmbi.1998.2354"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
comment|// https
name|assertEquals
argument_list|(
literal|"10.1006/jmbi.1998.2354"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"https://doi.org/10.1006/jmbi.1998.2354"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
comment|// https with % divider
name|assertEquals
argument_list|(
literal|"10.2307/1990888"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"https://dx.doi.org/10.2307%2F1990888"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
comment|// other domains
name|assertEquals
argument_list|(
literal|"10.1145/1294928.1294933"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://doi.acm.org/10.1145/1294928.1294933"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1145/1294928.1294933"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://doi.acm.net/10.1145/1294928.1294933"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1145/1294928.1294933"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://doi.acm.com/10.1145/1294928.1294933"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1145/1294928.1294933"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://doi.acm.de/10.1145/1294928.1294933"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1007/978-3-642-15618-2_19"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://dx.doi.org/10.1007/978-3-642-15618-2_19"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1007/978-3-642-15618-2_19"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://dx.doi.net/10.1007/978-3-642-15618-2_19"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1007/978-3-642-15618-2_19"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://dx.doi.com/10.1007/978-3-642-15618-2_19"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1007/978-3-642-15618-2_19"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://dx.doi.de/10.1007/978-3-642-15618-2_19"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.4108/ICST.COLLABORATECOM2009.8275"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://dx.doi.org/10.4108/ICST.COLLABORATECOM2009.8275"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1109/MIC.2012.43"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://doi.ieeecomputersociety.org/10.1109/MIC.2012.43"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|correctlyDecodeHttpDOIs ()
specifier|public
name|void
name|correctlyDecodeHttpDOIs
parameter_list|()
block|{
comment|// See http://www.doi.org/doi_handbook/2_Numbering.html#2.5.2.4
comment|// % -> (%25)
name|assertEquals
argument_list|(
literal|"10.1006/rwei.1999%.0001"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://doi.org/10.1006/rwei.1999%25.0001"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
comment|// " -> (%22)
name|assertEquals
argument_list|(
literal|"10.1006/rwei.1999\".0001"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://doi.org/10.1006/rwei.1999%22.0001"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
comment|// # -> (%23)
name|assertEquals
argument_list|(
literal|"10.1006/rwei.1999#.0001"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://doi.org/10.1006/rwei.1999%23.0001"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
comment|// SPACE -> (%20)
name|assertEquals
argument_list|(
literal|"10.1006/rwei.1999 .0001"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://doi.org/10.1006/rwei.1999%20.0001"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
comment|// ? -> (%3F)
name|assertEquals
argument_list|(
literal|"10.1006/rwei.1999?.0001"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"http://doi.org/10.1006/rwei.1999%3F.0001"
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|correctlyEncodeDOIs ()
specifier|public
name|void
name|correctlyEncodeDOIs
parameter_list|()
block|{
comment|// See http://www.doi.org/doi_handbook/2_Numbering.html#2.5.2.4
comment|// % -> (%25)
name|assertEquals
argument_list|(
literal|"https://doi.org/10.1006/rwei.1999%25.0001"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"https://doi.org/10.1006/rwei.1999%25.0001"
argument_list|)
operator|.
name|getURIAsASCIIString
argument_list|()
argument_list|)
expr_stmt|;
comment|// " -> (%22)
name|assertEquals
argument_list|(
literal|"https://doi.org/10.1006/rwei.1999%22.0001"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"https://doi.org/10.1006/rwei.1999%22.0001"
argument_list|)
operator|.
name|getURIAsASCIIString
argument_list|()
argument_list|)
expr_stmt|;
comment|// # -> (%23)
name|assertEquals
argument_list|(
literal|"https://doi.org/10.1006/rwei.1999%23.0001"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"https://doi.org/10.1006/rwei.1999%23.0001"
argument_list|)
operator|.
name|getURIAsASCIIString
argument_list|()
argument_list|)
expr_stmt|;
comment|// SPACE -> (%20)
name|assertEquals
argument_list|(
literal|"https://doi.org/10.1006/rwei.1999%20.0001"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"https://doi.org/10.1006/rwei.1999%20.0001"
argument_list|)
operator|.
name|getURIAsASCIIString
argument_list|()
argument_list|)
expr_stmt|;
comment|// ? -> (%3F)
name|assertEquals
argument_list|(
literal|"https://doi.org/10.1006/rwei.1999%3F.0001"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"https://doi.org/10.1006/rwei.1999%3F.0001"
argument_list|)
operator|.
name|getURIAsASCIIString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|constructCorrectURLForDoi ()
specifier|public
name|void
name|constructCorrectURLForDoi
parameter_list|()
block|{
comment|// add / to RESOLVER url if missing
name|assertEquals
argument_list|(
literal|"https://doi.org/10.1006/jmbi.1998.2354"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"10.1006/jmbi.1998.2354"
argument_list|)
operator|.
name|getURIAsASCIIString
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"https://doi.org/10.1006/jmbi.1998.2354"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"https://doi.org/10.1006/jmbi.1998.2354"
argument_list|)
operator|.
name|getURIAsASCIIString
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"https://doi.org/10.1109/VLHCC.2004.20"
argument_list|,
operator|new
name|DOI
argument_list|(
literal|"doi:10.1109/VLHCC.2004.20"
argument_list|)
operator|.
name|getURIAsASCIIString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findDoiInsideArbitraryText ()
specifier|public
name|void
name|findDoiInsideArbitraryText
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"10.1006/jmbi.1998.2354"
argument_list|,
name|DOI
operator|.
name|findInText
argument_list|(
literal|"other stuff 10.1006/jmbi.1998.2354 end"
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|noDOIFoundInsideArbitraryText ()
specifier|public
name|void
name|noDOIFoundInsideArbitraryText
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|DOI
operator|.
name|findInText
argument_list|(
literal|"text without 28282 a doi"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseDOIWithWhiteSpace ()
specifier|public
name|void
name|parseDOIWithWhiteSpace
parameter_list|()
block|{
name|String
name|doiWithSpace
init|=
literal|"https : / / doi.org / 10 .1109 /V LHCC.20 04.20"
decl_stmt|;
name|assertEquals
argument_list|(
literal|"https://doi.org/10.1109/VLHCC.2004.20"
argument_list|,
name|DOI
operator|.
name|parse
argument_list|(
name|doiWithSpace
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|getURIAsASCIIString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

