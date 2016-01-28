begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|MetaData
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
name|BibtexFieldFormatters
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
name|CaseChangers
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
name|Formatter
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
name|IdentityFormatter
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

begin_class
DECL|class|SaveActions
specifier|public
class|class
name|SaveActions
block|{
DECL|field|actions
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|Formatter
argument_list|>
name|actions
decl_stmt|;
DECL|field|availableFormatters
specifier|private
name|List
argument_list|<
name|Formatter
argument_list|>
name|availableFormatters
decl_stmt|;
DECL|field|META_KEY
specifier|public
specifier|static
specifier|final
name|String
name|META_KEY
init|=
literal|"saveActions"
decl_stmt|;
DECL|method|SaveActions (MetaData metaData)
specifier|public
name|SaveActions
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
name|actions
operator|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
expr_stmt|;
name|setAvailableFormatters
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|formatters
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|META_KEY
argument_list|)
decl_stmt|;
if|if
condition|(
name|formatters
operator|==
literal|null
condition|)
block|{
comment|// no save actions defined in the meta data
return|return;
block|}
else|else
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|formatters
operator|.
name|size
argument_list|()
condition|;
name|i
operator|+=
literal|2
control|)
block|{
try|try
block|{
name|String
name|field
init|=
name|formatters
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|Formatter
name|formatter
init|=
name|getFormatterFromString
argument_list|(
name|formatters
operator|.
name|get
argument_list|(
name|i
operator|+
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|actions
operator|.
name|put
argument_list|(
name|field
argument_list|,
name|formatter
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IndexOutOfBoundsException
name|e
parameter_list|)
block|{
comment|// the meta data string in the file is broken. -> Ignore the last item
break|break;
block|}
block|}
block|}
block|}
DECL|method|setAvailableFormatters ()
specifier|private
name|void
name|setAvailableFormatters
parameter_list|()
block|{
name|availableFormatters
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|availableFormatters
operator|.
name|addAll
argument_list|(
name|BibtexFieldFormatters
operator|.
name|ALL
argument_list|)
expr_stmt|;
name|availableFormatters
operator|.
name|addAll
argument_list|(
name|CaseChangers
operator|.
name|ALL
argument_list|)
expr_stmt|;
block|}
DECL|method|applySaveActions (BibEntry entry)
specifier|public
name|BibEntry
name|applySaveActions
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
for|for
control|(
name|String
name|key
range|:
name|actions
operator|.
name|keySet
argument_list|()
control|)
block|{
name|Formatter
name|formatter
init|=
name|actions
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|String
name|fieldContent
init|=
name|entry
operator|.
name|getField
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|String
name|formattedContent
init|=
name|formatter
operator|.
name|format
argument_list|(
name|fieldContent
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|key
argument_list|,
name|formattedContent
argument_list|)
expr_stmt|;
block|}
return|return
name|entry
return|;
block|}
DECL|method|getFormatterFromString (String formatterName)
specifier|private
name|Formatter
name|getFormatterFromString
parameter_list|(
name|String
name|formatterName
parameter_list|)
block|{
for|for
control|(
name|Formatter
name|formatter
range|:
name|availableFormatters
control|)
block|{
if|if
condition|(
name|formatterName
operator|.
name|equals
argument_list|(
name|formatter
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|formatter
return|;
block|}
block|}
return|return
operator|new
name|IdentityFormatter
argument_list|()
return|;
block|}
block|}
end_class

end_unit

