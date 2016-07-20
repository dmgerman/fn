begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.xmp
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|xmp
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|ParserConfigurationException
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
name|BibtexTestData
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
name|Globals
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|jempbox
operator|.
name|impl
operator|.
name|XMLUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|jempbox
operator|.
name|xmp
operator|.
name|XMPMetadata
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
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|NodeList
import|;
end_import

begin_class
DECL|class|XMPSchemaBibtexTest
specifier|public
class|class
name|XMPSchemaBibtexTest
block|{
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
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
DECL|method|assertEqualsBibtexEntry (BibEntry e, BibEntry x)
specifier|public
name|void
name|assertEqualsBibtexEntry
parameter_list|(
name|BibEntry
name|e
parameter_list|,
name|BibEntry
name|x
parameter_list|)
block|{
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|x
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|e
operator|.
name|getCiteKey
argument_list|()
argument_list|,
name|x
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|e
operator|.
name|getType
argument_list|()
argument_list|,
name|x
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|e
operator|.
name|getFieldNames
argument_list|()
operator|.
name|size
argument_list|()
argument_list|,
name|x
operator|.
name|getFieldNames
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|name
range|:
name|e
operator|.
name|getFieldNames
argument_list|()
control|)
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|e
operator|.
name|getFieldOptional
argument_list|(
name|name
argument_list|)
argument_list|,
name|x
operator|.
name|getFieldOptional
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testXMPSchemaBibtexXMPMetadata ()
specifier|public
name|void
name|testXMPSchemaBibtexXMPMetadata
parameter_list|()
throws|throws
name|IOException
block|{
name|XMPMetadata
name|xmp
init|=
operator|new
name|XMPMetadata
argument_list|()
decl_stmt|;
name|XMPSchemaBibtex
name|bibtex
init|=
operator|new
name|XMPSchemaBibtex
argument_list|(
name|xmp
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|bibtex
operator|.
name|getElement
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"rdf:Description"
argument_list|,
name|bibtex
operator|.
name|getElement
argument_list|()
operator|.
name|getTagName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testXMPSchemaBibtexElement ()
specifier|public
name|void
name|testXMPSchemaBibtexElement
parameter_list|()
throws|throws
name|ParserConfigurationException
block|{
name|DocumentBuilderFactory
name|builderFactory
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|DocumentBuilder
name|builder
init|=
name|builderFactory
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|Element
name|e
init|=
name|builder
operator|.
name|newDocument
argument_list|()
operator|.
name|createElement
argument_list|(
literal|"rdf:Description"
argument_list|)
decl_stmt|;
name|XMPSchemaBibtex
name|bibtex
init|=
operator|new
name|XMPSchemaBibtex
argument_list|(
name|e
argument_list|,
literal|"bibtex"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|e
argument_list|,
name|bibtex
operator|.
name|getElement
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"rdf:Description"
argument_list|,
name|bibtex
operator|.
name|getElement
argument_list|()
operator|.
name|getTagName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetSetPersonList ()
specifier|public
name|void
name|testGetSetPersonList
parameter_list|()
throws|throws
name|IOException
block|{
name|XMPMetadata
name|xmp
init|=
operator|new
name|XMPMetadata
argument_list|()
decl_stmt|;
name|XMPSchemaBibtex
name|bibtex
init|=
operator|new
name|XMPSchemaBibtex
argument_list|(
name|xmp
argument_list|)
decl_stmt|;
name|bibtex
operator|.
name|setPersonList
argument_list|(
literal|"author"
argument_list|,
literal|"Tom DeMarco and Kent Beck"
argument_list|)
expr_stmt|;
name|Element
name|e
init|=
name|bibtex
operator|.
name|getElement
argument_list|()
decl_stmt|;
name|NodeList
name|l1
init|=
name|e
operator|.
name|getElementsByTagName
argument_list|(
literal|"bibtex:author"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|l1
operator|.
name|getLength
argument_list|()
argument_list|)
expr_stmt|;
name|NodeList
name|l
init|=
name|e
operator|.
name|getElementsByTagName
argument_list|(
literal|"rdf:li"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|l
operator|.
name|getLength
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Tom DeMarco"
argument_list|,
name|XMLUtil
operator|.
name|getStringValue
argument_list|(
operator|(
name|Element
operator|)
name|l
operator|.
name|item
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Kent Beck"
argument_list|,
name|XMLUtil
operator|.
name|getStringValue
argument_list|(
operator|(
name|Element
operator|)
name|l
operator|.
name|item
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|authors
init|=
name|bibtex
operator|.
name|getPersonList
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|authors
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Tom DeMarco"
argument_list|,
name|authors
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Kent Beck"
argument_list|,
name|authors
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSetGetTextPropertyString ()
specifier|public
name|void
name|testSetGetTextPropertyString
parameter_list|()
throws|throws
name|IOException
block|{
name|XMPMetadata
name|xmp
init|=
operator|new
name|XMPMetadata
argument_list|()
decl_stmt|;
name|XMPSchemaBibtex
name|bibtex
init|=
operator|new
name|XMPSchemaBibtex
argument_list|(
name|xmp
argument_list|)
decl_stmt|;
name|bibtex
operator|.
name|setTextProperty
argument_list|(
literal|"title"
argument_list|,
literal|"The advanced Flux-Compensation for Delawney-Separation"
argument_list|)
expr_stmt|;
name|Element
name|e
init|=
name|bibtex
operator|.
name|getElement
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"The advanced Flux-Compensation for Delawney-Separation"
argument_list|,
name|e
operator|.
name|getAttribute
argument_list|(
literal|"bibtex:title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"The advanced Flux-Compensation for Delawney-Separation"
argument_list|,
name|bibtex
operator|.
name|getTextProperty
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|bibtex
operator|.
name|setTextProperty
argument_list|(
literal|"title"
argument_list|,
literal|"The advanced Flux-Correlation for Delawney-Separation"
argument_list|)
expr_stmt|;
name|e
operator|=
name|bibtex
operator|.
name|getElement
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"The advanced Flux-Correlation for Delawney-Separation"
argument_list|,
name|e
operator|.
name|getAttribute
argument_list|(
literal|"bibtex:title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"The advanced Flux-Correlation for Delawney-Separation"
argument_list|,
name|bibtex
operator|.
name|getTextProperty
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|bibtex
operator|.
name|setTextProperty
argument_list|(
literal|"abstract"
argument_list|,
literal|"   The abstract\n can go \n \n on several \n lines with \n many \n\n empty ones in \n between."
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"   The abstract\n can go \n \n on several \n lines with \n many \n\n empty ones in \n between."
argument_list|,
name|bibtex
operator|.
name|getTextProperty
argument_list|(
literal|"abstract"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSetGetBagListString ()
specifier|public
name|void
name|testSetGetBagListString
parameter_list|()
throws|throws
name|IOException
block|{
name|XMPMetadata
name|xmp
init|=
operator|new
name|XMPMetadata
argument_list|()
decl_stmt|;
name|XMPSchemaBibtex
name|bibtex
init|=
operator|new
name|XMPSchemaBibtex
argument_list|(
name|xmp
argument_list|)
decl_stmt|;
name|bibtex
operator|.
name|addBagValue
argument_list|(
literal|"author"
argument_list|,
literal|"Tom DeMarco"
argument_list|)
expr_stmt|;
name|bibtex
operator|.
name|addBagValue
argument_list|(
literal|"author"
argument_list|,
literal|"Kent Beck"
argument_list|)
expr_stmt|;
block|{
name|List
argument_list|<
name|String
argument_list|>
name|l
init|=
name|bibtex
operator|.
name|getBagList
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|l
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|l
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
literal|"Tom DeMarco"
argument_list|)
operator|||
name|l
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|.
name|equals
argument_list|(
literal|"Tom DeMarco"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|l
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
literal|"Kent Beck"
argument_list|)
operator|||
name|l
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|.
name|equals
argument_list|(
literal|"Kent Beck"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|{
name|bibtex
operator|.
name|removeBagValue
argument_list|(
literal|"author"
argument_list|,
literal|"Kent Beck"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|l
init|=
name|bibtex
operator|.
name|getBagList
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|l
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|l
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
literal|"Tom DeMarco"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|{
comment|// Already removed
name|bibtex
operator|.
name|removeBagValue
argument_list|(
literal|"author"
argument_list|,
literal|"Kent Beck"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|l
init|=
name|bibtex
operator|.
name|getBagList
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|l
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|l
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
literal|"Tom DeMarco"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|{
comment|// Duplicates allowed!
name|bibtex
operator|.
name|addBagValue
argument_list|(
literal|"author"
argument_list|,
literal|"Tom DeMarco"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|l
init|=
name|bibtex
operator|.
name|getBagList
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|l
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|l
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
literal|"Tom DeMarco"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|l
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|.
name|equals
argument_list|(
literal|"Tom DeMarco"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Removes both
name|bibtex
operator|.
name|removeBagValue
argument_list|(
literal|"author"
argument_list|,
literal|"Tom DeMarco"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|l
init|=
name|bibtex
operator|.
name|getBagList
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|l
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetSequenceListString ()
specifier|public
name|void
name|testGetSequenceListString
parameter_list|()
throws|throws
name|IOException
block|{
name|XMPMetadata
name|xmp
init|=
operator|new
name|XMPMetadata
argument_list|()
decl_stmt|;
name|XMPSchemaBibtex
name|bibtex
init|=
operator|new
name|XMPSchemaBibtex
argument_list|(
name|xmp
argument_list|)
decl_stmt|;
name|bibtex
operator|.
name|addSequenceValue
argument_list|(
literal|"author"
argument_list|,
literal|"Tom DeMarco"
argument_list|)
expr_stmt|;
name|bibtex
operator|.
name|addSequenceValue
argument_list|(
literal|"author"
argument_list|,
literal|"Kent Beck"
argument_list|)
expr_stmt|;
block|{
name|List
argument_list|<
name|String
argument_list|>
name|l
init|=
name|bibtex
operator|.
name|getSequenceList
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|l
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Tom DeMarco"
argument_list|,
name|l
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Kent Beck"
argument_list|,
name|l
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|{
name|bibtex
operator|.
name|removeSequenceValue
argument_list|(
literal|"author"
argument_list|,
literal|"Tom DeMarco"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|l
init|=
name|bibtex
operator|.
name|getSequenceList
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|l
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|l
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
literal|"Kent Beck"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|{
comment|// Already removed
name|bibtex
operator|.
name|removeSequenceValue
argument_list|(
literal|"author"
argument_list|,
literal|"Tom DeMarco"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|l
init|=
name|bibtex
operator|.
name|getSequenceList
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|l
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|l
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
literal|"Kent Beck"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|{
comment|// Duplicates allowed!
name|bibtex
operator|.
name|addSequenceValue
argument_list|(
literal|"author"
argument_list|,
literal|"Kent Beck"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|l
init|=
name|bibtex
operator|.
name|getSequenceList
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|l
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|l
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
literal|"Kent Beck"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|l
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|.
name|equals
argument_list|(
literal|"Kent Beck"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Remvoes all
name|bibtex
operator|.
name|removeSequenceValue
argument_list|(
literal|"author"
argument_list|,
literal|"Kent Beck"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|l
init|=
name|bibtex
operator|.
name|getSequenceList
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|l
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetAllProperties ()
specifier|public
name|void
name|testGetAllProperties
parameter_list|()
throws|throws
name|IOException
block|{
name|XMPMetadata
name|xmp
init|=
operator|new
name|XMPMetadata
argument_list|()
decl_stmt|;
name|XMPSchemaBibtex
name|bibtex
init|=
operator|new
name|XMPSchemaBibtex
argument_list|(
name|xmp
argument_list|)
decl_stmt|;
name|bibtex
operator|.
name|setTextProperty
argument_list|(
literal|"title"
argument_list|,
literal|"BlaBla Ta Ta\nHello World"
argument_list|)
expr_stmt|;
name|bibtex
operator|.
name|setTextProperty
argument_list|(
literal|"abstract"
argument_list|,
literal|"BlaBla Ta Ta\nHello World"
argument_list|)
expr_stmt|;
name|bibtex
operator|.
name|setTextProperty
argument_list|(
literal|"review"
argument_list|,
literal|"BlaBla Ta Ta\nHello World"
argument_list|)
expr_stmt|;
name|bibtex
operator|.
name|setTextProperty
argument_list|(
literal|"note"
argument_list|,
literal|"BlaBla Ta Ta\nHello World"
argument_list|)
expr_stmt|;
name|bibtex
operator|.
name|setPersonList
argument_list|(
literal|"author"
argument_list|,
literal|"Mouse, Mickey and Bond, James"
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|s
init|=
name|XMPSchemaBibtex
operator|.
name|getAllProperties
argument_list|(
name|bibtex
argument_list|,
literal|"bibtex"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|5
argument_list|,
name|s
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|s
operator|.
name|containsKey
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|s
operator|.
name|containsKey
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"BlaBla Ta Ta Hello World"
argument_list|,
name|s
operator|.
name|get
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"BlaBla Ta Ta\nHello World"
argument_list|,
name|s
operator|.
name|get
argument_list|(
literal|"abstract"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"BlaBla Ta Ta\nHello World"
argument_list|,
name|s
operator|.
name|get
argument_list|(
literal|"review"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"BlaBla Ta Ta\nHello World"
argument_list|,
name|s
operator|.
name|get
argument_list|(
literal|"note"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Mickey Mouse and James Bond"
argument_list|,
name|s
operator|.
name|get
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSetBibtexEntry ()
specifier|public
name|void
name|testSetBibtexEntry
parameter_list|()
throws|throws
name|IOException
block|{
name|XMPMetadata
name|xmp
init|=
operator|new
name|XMPMetadata
argument_list|()
decl_stmt|;
name|XMPSchemaBibtex
name|bibtex
init|=
operator|new
name|XMPSchemaBibtex
argument_list|(
name|xmp
argument_list|)
decl_stmt|;
name|BibEntry
name|e
init|=
name|BibtexTestData
operator|.
name|getBibtexEntry
argument_list|()
decl_stmt|;
name|bibtex
operator|.
name|setBibtexEntry
argument_list|(
name|e
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|BibEntry
name|e2
init|=
name|bibtex
operator|.
name|getBibtexEntry
argument_list|()
decl_stmt|;
name|assertEqualsBibtexEntry
argument_list|(
name|e
argument_list|,
name|e2
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetTextContent ()
specifier|public
name|void
name|testGetTextContent
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|bibtexString
init|=
literal|"<bibtex:year>2003</bibtex:year>\n"
operator|+
literal|"<bibtex:title>\n   "
operator|+
literal|"Beach sand convolution by surf-wave optimzation\n"
operator|+
literal|"</bibtex:title>\n"
operator|+
literal|"<bibtex:bibtexkey>OezbekC06</bibtex:bibtexkey>\n"
decl_stmt|;
name|bibtexString
operator|=
name|XMPUtilTest
operator|.
name|bibtexXPacket
argument_list|(
name|XMPUtilTest
operator|.
name|bibtexDescription
argument_list|(
name|bibtexString
argument_list|)
argument_list|)
expr_stmt|;
name|Document
name|d
init|=
name|XMLUtil
operator|.
name|parse
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|bibtexString
operator|.
name|getBytes
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Beach sand convolution by surf-wave optimzation"
argument_list|,
name|XMPSchemaBibtex
operator|.
name|getTextContent
argument_list|(
name|d
operator|.
name|getElementsByTagName
argument_list|(
literal|"bibtex:title"
argument_list|)
operator|.
name|item
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

