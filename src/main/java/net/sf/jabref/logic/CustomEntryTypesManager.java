begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
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
name|JabRefPreferences
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
name|CustomEntryType
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
name|bibtex
operator|.
name|EntryTypes
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
name|EntryType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|io
operator|.
name|Writer
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
name|Iterator
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
DECL|class|CustomEntryTypesManager
specifier|public
class|class
name|CustomEntryTypesManager
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|CustomEntryTypesManager
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|ALL
specifier|public
specifier|static
specifier|final
name|List
argument_list|<
name|EntryType
argument_list|>
name|ALL
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|/**      * Load all custom entry types from preferences. This method is      * called from JabRef when the program starts.      */
DECL|method|loadCustomEntryTypes (JabRefPreferences prefs)
specifier|public
specifier|static
name|void
name|loadCustomEntryTypes
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|int
name|number
init|=
literal|0
decl_stmt|;
name|CustomEntryType
name|type
decl_stmt|;
while|while
condition|(
operator|(
name|type
operator|=
name|prefs
operator|.
name|getCustomEntryType
argument_list|(
name|number
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|EntryTypes
operator|.
name|addOrModifyCustomEntryType
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|ALL
operator|.
name|add
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|number
operator|++
expr_stmt|;
block|}
block|}
comment|/**      * Iterate through all entry types, and store those that are      * custom defined to preferences. This method is called from      * JabRefFrame when the program closes.      */
DECL|method|saveCustomEntryTypes (JabRefPreferences prefs)
specifier|public
specifier|static
name|void
name|saveCustomEntryTypes
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|Iterator
argument_list|<
name|String
argument_list|>
name|iterator
init|=
name|EntryTypes
operator|.
name|getAllTypes
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|int
name|number
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|iterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|EntryType
name|entryType
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|iterator
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|entryType
operator|instanceof
name|CustomEntryType
condition|)
block|{
comment|// Store this entry type.
name|prefs
operator|.
name|storeCustomEntryType
argument_list|(
operator|(
name|CustomEntryType
operator|)
name|entryType
argument_list|,
name|number
argument_list|)
expr_stmt|;
name|number
operator|++
expr_stmt|;
block|}
block|}
comment|// Then, if there are more 'old' custom types defined, remove these
comment|// from preferences. This is necessary if the number of custom types
comment|// has decreased.
name|prefs
operator|.
name|purgeCustomEntryTypes
argument_list|(
name|number
argument_list|)
expr_stmt|;
block|}
DECL|method|save (CustomEntryType entry, Writer out)
specifier|public
specifier|static
name|void
name|save
parameter_list|(
name|CustomEntryType
name|entry
parameter_list|,
name|Writer
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
literal|"@comment{"
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
name|CustomEntryType
operator|.
name|ENTRYTYPE_FLAG
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
name|entry
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
literal|": req["
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
name|entry
operator|.
name|getRequiredFieldsString
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
literal|"] opt["
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
name|String
operator|.
name|join
argument_list|(
literal|";"
argument_list|,
name|entry
operator|.
name|getOptionalFields
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
literal|"]}"
argument_list|)
expr_stmt|;
block|}
DECL|method|parseEntryType (String comment)
specifier|public
specifier|static
name|CustomEntryType
name|parseEntryType
parameter_list|(
name|String
name|comment
parameter_list|)
block|{
try|try
block|{
name|String
name|rest
decl_stmt|;
name|rest
operator|=
name|comment
operator|.
name|substring
argument_list|(
name|CustomEntryType
operator|.
name|ENTRYTYPE_FLAG
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|nPos
init|=
name|rest
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|rest
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|nPos
argument_list|)
decl_stmt|;
name|rest
operator|=
name|rest
operator|.
name|substring
argument_list|(
name|nPos
operator|+
literal|2
argument_list|)
expr_stmt|;
name|int
name|rPos
init|=
name|rest
operator|.
name|indexOf
argument_list|(
literal|']'
argument_list|)
decl_stmt|;
if|if
condition|(
name|rPos
operator|<
literal|4
condition|)
block|{
throw|throw
operator|new
name|IndexOutOfBoundsException
argument_list|()
throw|;
block|}
name|String
name|reqFields
init|=
name|rest
operator|.
name|substring
argument_list|(
literal|4
argument_list|,
name|rPos
argument_list|)
decl_stmt|;
name|int
name|oPos
init|=
name|rest
operator|.
name|indexOf
argument_list|(
literal|']'
argument_list|,
name|rPos
operator|+
literal|1
argument_list|)
decl_stmt|;
name|String
name|optFields
init|=
name|rest
operator|.
name|substring
argument_list|(
name|rPos
operator|+
literal|6
argument_list|,
name|oPos
argument_list|)
decl_stmt|;
return|return
operator|new
name|CustomEntryType
argument_list|(
name|name
argument_list|,
name|reqFields
argument_list|,
name|optFields
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IndexOutOfBoundsException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Ill-formed entrytype comment in BibTeX file."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

