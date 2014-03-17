begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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

begin_comment
comment|/**  * This class is used to represent customized entry types.  *  */
end_comment

begin_class
DECL|class|CustomEntryType
specifier|public
class|class
name|CustomEntryType
extends|extends
name|BibtexEntryType
block|{
DECL|field|name
specifier|private
name|String
name|name
decl_stmt|;
DECL|field|req
DECL|field|opt
DECL|field|priOpt
specifier|private
name|String
index|[]
name|req
decl_stmt|,
name|opt
decl_stmt|,
name|priOpt
decl_stmt|;
DECL|field|reqSets
specifier|private
name|String
index|[]
index|[]
name|reqSets
init|=
literal|null
decl_stmt|;
comment|// Sets of either-or required fields, if any
DECL|method|CustomEntryType (String name_, String[] req_, String[] opt_, String[] opt2_)
specifier|public
name|CustomEntryType
parameter_list|(
name|String
name|name_
parameter_list|,
name|String
index|[]
name|req_
parameter_list|,
name|String
index|[]
name|opt_
parameter_list|,
name|String
index|[]
name|opt2_
parameter_list|)
block|{
name|name
operator|=
name|name_
expr_stmt|;
name|parseRequiredFields
argument_list|(
name|req_
argument_list|)
expr_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|allOpt
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|anOpt_
range|:
name|opt_
control|)
name|allOpt
operator|.
name|add
argument_list|(
name|anOpt_
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|anOpt2_
range|:
name|opt2_
control|)
name|allOpt
operator|.
name|add
argument_list|(
name|anOpt2_
argument_list|)
expr_stmt|;
name|opt
operator|=
name|allOpt
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|allOpt
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
name|priOpt
operator|=
name|opt_
expr_stmt|;
block|}
DECL|method|CustomEntryType (String name_, String[] req_, String[] opt_)
specifier|public
name|CustomEntryType
parameter_list|(
name|String
name|name_
parameter_list|,
name|String
index|[]
name|req_
parameter_list|,
name|String
index|[]
name|opt_
parameter_list|)
block|{
name|this
argument_list|(
name|name_
argument_list|,
name|req_
argument_list|,
name|opt_
argument_list|,
operator|new
name|String
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
DECL|method|CustomEntryType (String name_, String reqStr, String optStr)
specifier|public
name|CustomEntryType
parameter_list|(
name|String
name|name_
parameter_list|,
name|String
name|reqStr
parameter_list|,
name|String
name|optStr
parameter_list|)
block|{
name|name
operator|=
name|name_
expr_stmt|;
if|if
condition|(
name|reqStr
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|req
operator|=
operator|new
name|String
index|[
literal|0
index|]
expr_stmt|;
else|else
block|{
name|parseRequiredFields
argument_list|(
name|reqStr
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|optStr
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|opt
operator|=
operator|new
name|String
index|[
literal|0
index|]
expr_stmt|;
else|else
name|opt
operator|=
name|optStr
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
block|}
DECL|method|parseRequiredFields (String reqStr)
specifier|protected
name|void
name|parseRequiredFields
parameter_list|(
name|String
name|reqStr
parameter_list|)
block|{
name|String
index|[]
name|parts
init|=
name|reqStr
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
decl_stmt|;
name|parseRequiredFields
argument_list|(
name|parts
argument_list|)
expr_stmt|;
block|}
DECL|method|parseRequiredFields (String[] parts)
specifier|protected
name|void
name|parseRequiredFields
parameter_list|(
name|String
index|[]
name|parts
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|fields
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|String
index|[]
argument_list|>
name|sets
init|=
operator|new
name|ArrayList
argument_list|<
name|String
index|[]
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|part
range|:
name|parts
control|)
block|{
name|String
index|[]
name|subParts
init|=
name|part
operator|.
name|split
argument_list|(
literal|"/"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|subPart
range|:
name|subParts
control|)
block|{
name|fields
operator|.
name|add
argument_list|(
name|subPart
argument_list|)
expr_stmt|;
block|}
comment|// Check if we have either/or fields:
if|if
condition|(
name|subParts
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|sets
operator|.
name|add
argument_list|(
name|subParts
argument_list|)
expr_stmt|;
block|}
block|}
name|req
operator|=
name|fields
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|fields
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|sets
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|reqSets
operator|=
name|sets
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|sets
operator|.
name|size
argument_list|()
index|]
index|[]
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
DECL|method|getOptionalFields ()
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
name|opt
return|;
block|}
DECL|method|getRequiredFields ()
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
name|req
return|;
block|}
annotation|@
name|Override
DECL|method|getPrimaryOptionalFields ()
specifier|public
name|String
index|[]
name|getPrimaryOptionalFields
parameter_list|()
block|{
return|return
name|priOpt
return|;
block|}
DECL|method|getRequiredFieldsForCustomization ()
specifier|public
name|String
index|[]
name|getRequiredFieldsForCustomization
parameter_list|()
block|{
return|return
name|getRequiredFieldsString
argument_list|()
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
return|;
block|}
comment|//    public boolean isTemporary
DECL|method|describeRequiredFields ()
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|req
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|req
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
operator|(
operator|(
name|i
operator|<=
name|req
operator|.
name|length
operator|-
literal|1
operator|)
operator|&&
operator|(
name|req
operator|.
name|length
operator|>
literal|1
operator|)
operator|)
condition|?
literal|", "
else|:
literal|""
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|describeOptionalFields ()
specifier|public
name|String
name|describeOptionalFields
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|opt
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|opt
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
operator|(
operator|(
name|i
operator|<=
name|opt
operator|.
name|length
operator|-
literal|1
operator|)
operator|&&
operator|(
name|opt
operator|.
name|length
operator|>
literal|1
operator|)
operator|)
condition|?
literal|", "
else|:
literal|""
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Check whether this entry's required fields are set, taking crossreferenced entries and      * either-or fields into account:      * @param entry The entry to check.      * @param database The entry's database.      * @return True if required fields are set, false otherwise.      */
DECL|method|hasAllRequiredFields (BibtexEntry entry, BibtexDatabase database)
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
comment|// First check if the bibtex key is set:
if|if
condition|(
name|entry
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
operator|==
literal|null
condition|)
return|return
literal|false
return|;
comment|// Then check other fields:
name|boolean
index|[]
name|isSet
init|=
operator|new
name|boolean
index|[
name|req
operator|.
name|length
index|]
decl_stmt|;
comment|// First check for all fields, whether they are set here or in a crossref'd entry:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|req
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|isSet
index|[
name|i
index|]
operator|=
name|BibtexDatabase
operator|.
name|getResolvedField
argument_list|(
name|req
index|[
name|i
index|]
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
operator|!=
literal|null
expr_stmt|;
comment|// Then go through all fields. If a field is not set, see if it is part of an either-or
comment|// set where another field is set. If not, return false:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|req
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|isSet
index|[
name|i
index|]
condition|)
block|{
if|if
condition|(
operator|!
name|isCoupledFieldSet
argument_list|(
name|req
index|[
name|i
index|]
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
block|}
comment|// Passed all fields, so return true:
return|return
literal|true
return|;
block|}
DECL|method|isCoupledFieldSet (String field, BibtexEntry entry, BibtexDatabase database)
specifier|protected
name|boolean
name|isCoupledFieldSet
parameter_list|(
name|String
name|field
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
if|if
condition|(
name|reqSets
operator|==
literal|null
condition|)
return|return
literal|false
return|;
for|for
control|(
name|String
index|[]
name|reqSet
range|:
name|reqSets
control|)
block|{
name|boolean
name|takesPart
init|=
literal|false
decl_stmt|,
name|oneSet
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|reqSet
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
comment|// If this is the field we're looking for, note that the field is part of the set:
if|if
condition|(
name|reqSet
index|[
name|j
index|]
operator|.
name|equalsIgnoreCase
argument_list|(
name|field
argument_list|)
condition|)
name|takesPart
operator|=
literal|true
expr_stmt|;
comment|// If it is a different field, check if it is set:
elseif|else
if|if
condition|(
name|BibtexDatabase
operator|.
name|getResolvedField
argument_list|(
name|reqSet
index|[
name|j
index|]
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
operator|!=
literal|null
condition|)
name|oneSet
operator|=
literal|true
expr_stmt|;
block|}
comment|// Ths the field is part of the set, and at least one other field is set, return true:
if|if
condition|(
name|takesPart
operator|&&
name|oneSet
condition|)
return|return
literal|true
return|;
block|}
comment|// No hits, so return false:
return|return
literal|false
return|;
block|}
comment|/**      * Get a String describing the required field set for this entry type.      * @return Description of required field set for storage in preferences or bib file.      */
DECL|method|getRequiredFieldsString ()
specifier|public
name|String
name|getRequiredFieldsString
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|reqSetsPiv
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|req
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|(
name|reqSets
operator|==
literal|null
operator|)
operator|||
operator|(
name|reqSetsPiv
operator|==
name|reqSets
operator|.
name|length
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|req
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|req
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|reqSets
index|[
name|reqSetsPiv
index|]
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|reqSets
index|[
name|reqSetsPiv
index|]
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|reqSets
index|[
name|reqSetsPiv
index|]
index|[
name|j
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|j
operator|<
name|reqSets
index|[
name|reqSetsPiv
index|]
operator|.
name|length
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"/"
argument_list|)
expr_stmt|;
block|}
comment|// Skip next n-1 fields:
name|i
operator|+=
name|reqSets
index|[
name|reqSetsPiv
index|]
operator|.
name|length
operator|-
literal|1
expr_stmt|;
name|reqSetsPiv
operator|++
expr_stmt|;
block|}
else|else
name|sb
operator|.
name|append
argument_list|(
name|req
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|req
operator|.
name|length
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|save (Writer out)
specifier|public
name|void
name|save
parameter_list|(
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
literal|"@comment{"
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
name|GUIGlobals
operator|.
name|ENTRYTYPE_FLAG
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
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
name|getRequiredFieldsString
argument_list|()
argument_list|)
expr_stmt|;
comment|/*StringBuffer sb = new StringBuffer(); 	for (int i=0; i<req.length; i++) { 	    sb.append(req[i]); 	    if (i<req.length-1) 		sb.append(";"); 	} 	out.write(sb.toString());*/
name|out
operator|.
name|write
argument_list|(
literal|"] opt["
argument_list|)
expr_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|opt
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|opt
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|opt
operator|.
name|length
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|write
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
literal|"]}"
operator|+
name|Globals
operator|.
name|NEWLINE
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
comment|//if ((comment.length()< 9+GUIGlobals.ENTRYTYPE_FLAG.length())
comment|//	|| comment
comment|//System.out.println(">"+comment+"<");
name|String
name|rest
decl_stmt|;
name|rest
operator|=
name|comment
operator|.
name|substring
argument_list|(
name|GUIGlobals
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
throw|throw
operator|new
name|IndexOutOfBoundsException
argument_list|()
throw|;
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
comment|//System.out.println(name+"\nr '"+reqFields+"'");
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
comment|//System.out.println("o '"+optFields+"'");
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
name|Globals
operator|.
name|logger
argument_list|(
literal|"Ill-formed entrytype comment in BibTeX file."
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

