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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeMap
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
name|exporter
operator|.
name|FieldFormatterCleanups
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
name|cleanup
operator|.
name|FieldFormatterCleanup
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
name|formatter
operator|.
name|casechanger
operator|.
name|LowerCaseFormatter
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
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_class
DECL|class|MetaDataTest
specifier|public
class|class
name|MetaDataTest
block|{
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
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
name|metaData
operator|=
operator|new
name|MetaData
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|serializeNewMetadataReturnsEmptyMap ()
specifier|public
name|void
name|serializeNewMetadataReturnsEmptyMap
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyMap
argument_list|()
argument_list|,
name|metaData
operator|.
name|getAsStringMap
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|serializeSingleSaveAction ()
specifier|public
name|void
name|serializeSingleSaveAction
parameter_list|()
block|{
name|FieldFormatterCleanups
name|saveActions
init|=
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|LowerCaseFormatter
argument_list|()
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|metaData
operator|.
name|setSaveActions
argument_list|(
name|saveActions
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|expectedSerialization
init|=
operator|new
name|TreeMap
argument_list|<>
argument_list|()
decl_stmt|;
name|expectedSerialization
operator|.
name|put
argument_list|(
literal|"saveActions"
argument_list|,
literal|"enabled;"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"title[lower_case]"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|";"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedSerialization
argument_list|,
name|metaData
operator|.
name|getAsStringMap
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

