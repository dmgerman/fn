begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.l10n
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|stream
operator|.
name|Collectors
import|;
end_import

begin_class
DECL|class|Encodings
specifier|public
class|class
name|Encodings
block|{
DECL|field|ENCODINGS
specifier|public
specifier|static
specifier|final
name|Charset
index|[]
name|ENCODINGS
decl_stmt|;
DECL|field|ENCODINGS_DISPLAYNAMES
specifier|public
specifier|static
specifier|final
name|String
index|[]
name|ENCODINGS_DISPLAYNAMES
decl_stmt|;
DECL|field|encodingsList
specifier|private
specifier|static
name|List
argument_list|<
name|Charset
argument_list|>
name|encodingsList
init|=
name|Charset
operator|.
name|availableCharsets
argument_list|()
operator|.
name|values
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|distinct
argument_list|()
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
DECL|method|Encodings ()
specifier|private
name|Encodings
parameter_list|()
block|{     }
static|static
block|{
name|List
argument_list|<
name|String
argument_list|>
name|encodingsStringList
init|=
name|encodingsList
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Charset
operator|::
name|displayName
argument_list|)
operator|.
name|distinct
argument_list|()
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|ENCODINGS
operator|=
name|encodingsList
operator|.
name|toArray
argument_list|(
operator|new
name|Charset
index|[
name|encodingsList
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
name|ENCODINGS_DISPLAYNAMES
operator|=
name|encodingsStringList
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|encodingsStringList
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
block|}
DECL|method|getCharsets ()
specifier|public
specifier|static
name|List
argument_list|<
name|Charset
argument_list|>
name|getCharsets
parameter_list|()
block|{
return|return
name|encodingsList
return|;
block|}
block|}
end_class

end_unit

