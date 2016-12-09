begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
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
name|model
operator|.
name|groups
operator|.
name|AllEntriesGroup
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
name|groups
operator|.
name|SimpleKeywordGroup
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
name|groups
operator|.
name|ExplicitGroup
import|;
end_import

begin_comment
comment|/**  * Specifies how metadata is read and written.  */
end_comment

begin_class
DECL|class|MetadataSerializationConfiguration
specifier|public
class|class
name|MetadataSerializationConfiguration
block|{
comment|/**      * Character used for quoting in the string representation.      */
DECL|field|GROUP_QUOTE_CHAR
specifier|public
specifier|static
specifier|final
name|char
name|GROUP_QUOTE_CHAR
init|=
literal|'\\'
decl_stmt|;
comment|/**      * For separating units (e.g. name and hierarchic context) in the string representation      */
DECL|field|GROUP_UNIT_SEPARATOR
specifier|public
specifier|static
specifier|final
name|String
name|GROUP_UNIT_SEPARATOR
init|=
literal|";"
decl_stmt|;
comment|/**      * Identifier for {@link SimpleKeywordGroup}.      */
DECL|field|KEYWORD_GROUP_ID
specifier|public
specifier|static
specifier|final
name|String
name|KEYWORD_GROUP_ID
init|=
literal|"KeywordGroup:"
decl_stmt|;
comment|/**      * Identifier for {@link AllEntriesGroup}.      */
DECL|field|ALL_ENTRIES_GROUP_ID
specifier|public
specifier|static
specifier|final
name|String
name|ALL_ENTRIES_GROUP_ID
init|=
literal|"AllEntriesGroup:"
decl_stmt|;
comment|/**      * Identifier for {@link ExplicitGroup}.      */
DECL|field|EXPLICIT_GROUP_ID
specifier|public
specifier|static
specifier|final
name|String
name|EXPLICIT_GROUP_ID
init|=
literal|"ExplicitGroup:"
decl_stmt|;
block|}
end_class

end_unit

