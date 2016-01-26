begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

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
name|Locale
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
name|SortedSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringJoiner
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|Strings
import|;
end_import

begin_class
DECL|class|CanonicalBibtexEntry
specifier|public
class|class
name|CanonicalBibtexEntry
block|{
comment|/**      * This returns a canonical BibTeX serialization. Special characters such as "{" or "&" are NOT escaped, but written      * as is      *      * Serializes all fields, even the JabRef internal ones. Does NOT serialize "KEY_FIELD" as field, but as key      */
DECL|method|getCanonicalRepresentation (BibEntry e)
specifier|public
specifier|static
name|String
name|getCanonicalRepresentation
parameter_list|(
name|BibEntry
name|e
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
comment|// generate first line: type and bibtex key
name|String
name|citeKey
init|=
name|Strings
operator|.
name|nullToEmpty
argument_list|(
name|e
operator|.
name|getCiteKey
argument_list|()
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"@%s{%s,"
argument_list|,
name|e
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
argument_list|,
name|citeKey
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
comment|// we have to introduce a new Map as fields are stored case-sensitive in JabRef (see https://github.com/koppor/jabref/issues/45).
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|mapFieldToValue
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// determine sorted fields -- all fields lower case
name|SortedSet
argument_list|<
name|String
argument_list|>
name|sortedFields
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|fieldName
range|:
name|e
operator|.
name|getFieldNames
argument_list|()
control|)
block|{
comment|// JabRef stores the key in the field KEY_FIELD, which must not be serialized
if|if
condition|(
operator|!
name|fieldName
operator|.
name|equals
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
condition|)
block|{
name|String
name|lowerCaseFieldName
init|=
name|fieldName
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|sortedFields
operator|.
name|add
argument_list|(
name|lowerCaseFieldName
argument_list|)
expr_stmt|;
name|mapFieldToValue
operator|.
name|put
argument_list|(
name|lowerCaseFieldName
argument_list|,
name|e
operator|.
name|getField
argument_list|(
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// generate field entries
name|StringJoiner
name|sj
init|=
operator|new
name|StringJoiner
argument_list|(
literal|",\n"
argument_list|,
literal|""
argument_list|,
literal|"\n"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|fieldName
range|:
name|sortedFields
control|)
block|{
name|String
name|line
init|=
name|String
operator|.
name|format
argument_list|(
literal|"  %s = {%s}"
argument_list|,
name|fieldName
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|mapFieldToValue
operator|.
name|get
argument_list|(
name|fieldName
argument_list|)
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\r\\n"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
decl_stmt|;
name|sj
operator|.
name|add
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|sj
argument_list|)
expr_stmt|;
comment|// append the closing entry bracket
name|sb
operator|.
name|append
argument_list|(
literal|'}'
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

