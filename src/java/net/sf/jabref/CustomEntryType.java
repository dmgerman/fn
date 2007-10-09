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
name|Writer
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
specifier|private
name|String
index|[]
name|req
decl_stmt|,
name|opt
decl_stmt|;
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
name|name
operator|=
name|name_
expr_stmt|;
name|req
operator|=
name|req_
expr_stmt|;
name|opt
operator|=
name|opt_
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
name|req
operator|=
name|reqStr
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
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
if|if
condition|(
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
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
literal|true
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
literal|"] opt["
argument_list|)
expr_stmt|;
name|sb
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
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

