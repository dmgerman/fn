begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

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
name|io
operator|.
name|StringReader
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
name|importer
operator|.
name|ParserResult
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
name|importer
operator|.
name|fileformat
operator|.
name|BibtexParser
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
name|database
operator|.
name|BibDatabase
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

begin_class
DECL|class|BibtexTestData
specifier|public
class|class
name|BibtexTestData
block|{
DECL|method|getBibtexEntry ()
specifier|public
specifier|static
name|BibEntry
name|getBibtexEntry
parameter_list|()
throws|throws
name|IOException
block|{
name|BibDatabase
name|database
init|=
name|getBibtexDatabase
argument_list|()
decl_stmt|;
return|return
name|database
operator|.
name|getEntryByKey
argument_list|(
literal|"HipKro03"
argument_list|)
return|;
block|}
DECL|method|getBibtexDatabase ()
specifier|public
specifier|static
name|BibDatabase
name|getBibtexDatabase
parameter_list|()
throws|throws
name|IOException
block|{
name|StringReader
name|reader
init|=
operator|new
name|StringReader
argument_list|(
literal|"@ARTICLE{HipKro03,\n"
operator|+
literal|"  author = {Eric von Hippel and Georg von Krogh},\n"
operator|+
literal|"  title = {Open Source Software and the \"Private-Collective\" Innovation Model: Issues for Organization Science},\n"
operator|+
literal|"  journal = {Organization Science},\n"
operator|+
literal|"  year = {2003},\n"
operator|+
literal|"  volume = {14},\n"
operator|+
literal|"  pages = {209--223},\n"
operator|+
literal|"  number = {2},\n"
operator|+
literal|"  address = {Institute for Operations Research and the Management Sciences (INFORMS), Linthicum, Maryland, USA},\n"
operator|+
literal|"  doi = {http://dx.doi.org/10.1287/orsc.14.2.209.14992},"
operator|+
literal|"\n"
operator|+
literal|"  issn = {1526-5455},"
operator|+
literal|"\n"
operator|+
literal|"  publisher = {INFORMS}\n"
operator|+
literal|"}"
argument_list|)
decl_stmt|;
name|BibtexParser
name|parser
init|=
operator|new
name|BibtexParser
argument_list|(
name|reader
argument_list|)
decl_stmt|;
name|ParserResult
name|result
init|=
name|parser
operator|.
name|parse
argument_list|()
decl_stmt|;
return|return
name|result
operator|.
name|getDatabase
argument_list|()
return|;
block|}
block|}
end_class

end_unit

