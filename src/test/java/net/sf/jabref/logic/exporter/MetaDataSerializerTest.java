begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
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
name|bibtexkeypattern
operator|.
name|AbstractBibtexKeyPattern
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
name|bibtexkeypattern
operator|.
name|GlobalBibtexKeyPattern
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
name|model
operator|.
name|cleanup
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
name|model
operator|.
name|metadata
operator|.
name|ContentSelector
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
name|metadata
operator|.
name|MetaData
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
name|util
operator|.
name|ArrayList
import|;
end_import

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
DECL|class|MetaDataSerializerTest
specifier|public
class|class
name|MetaDataSerializerTest
block|{
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
DECL|field|pattern
specifier|private
name|GlobalBibtexKeyPattern
name|pattern
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|metaData
operator|=
operator|new
name|MetaData
argument_list|()
expr_stmt|;
name|pattern
operator|=
operator|new
name|GlobalBibtexKeyPattern
argument_list|(
name|AbstractBibtexKeyPattern
operator|.
name|split
argument_list|(
literal|"[auth][year]"
argument_list|)
argument_list|)
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
name|MetaDataSerializer
operator|.
name|getSerializedStringMap
argument_list|(
name|metaData
argument_list|,
name|pattern
argument_list|)
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
name|OS
operator|.
name|NEWLINE
operator|+
literal|"title[lower_case]"
operator|+
name|OS
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
name|MetaDataSerializer
operator|.
name|getSerializedStringMap
argument_list|(
name|metaData
argument_list|,
name|pattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|serializeSingleContentSelectors ()
specifier|public
name|void
name|serializeSingleContentSelectors
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|values
init|=
operator|new
name|ArrayList
argument_list|(
literal|4
argument_list|)
decl_stmt|;
name|values
operator|.
name|add
argument_list|(
literal|"approved"
argument_list|)
expr_stmt|;
name|values
operator|.
name|add
argument_list|(
literal|"captured"
argument_list|)
expr_stmt|;
name|values
operator|.
name|add
argument_list|(
literal|"received"
argument_list|)
expr_stmt|;
name|values
operator|.
name|add
argument_list|(
literal|"status"
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|addContentSelector
argument_list|(
operator|new
name|ContentSelector
argument_list|(
literal|"status"
argument_list|,
name|values
argument_list|)
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
name|MetaData
operator|.
name|SELECTOR_META_PREFIX
operator|+
literal|"status"
argument_list|,
literal|"approved;captured;received;status;"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedSerialization
argument_list|,
name|MetaDataSerializer
operator|.
name|getSerializedStringMap
argument_list|(
name|metaData
argument_list|,
name|pattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

