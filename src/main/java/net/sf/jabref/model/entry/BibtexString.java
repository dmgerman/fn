begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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

begin_comment
comment|/**  * This class models a BibTex String ("@String")  */
end_comment

begin_class
DECL|class|BibtexString
specifier|public
class|class
name|BibtexString
block|{
comment|/**      * Type of a \@String.      *<p>      * Differentiate a \@String based on its usage:      *<p>      * - {@link #AUTHOR}: prefix "a", for author and editor fields.      * - {@link #INSTITUTION}: prefix "i", for institution and organization      * field      * - {@link #PUBLISHER}: prefix "p", for publisher fields      * - {@link #OTHER}: no prefix, for any field      *<p>      * Examples:      *<p>      * \@String { aKahle    = "Kahle, Brewster " } -> author      * \@String { aStallman = "Stallman, Richard" } -> author      * \@String { iMIT      = "{Massachusetts Institute of Technology ({MIT})}" } -> institution      * \@String { pMIT      = "{Massachusetts Institute of Technology ({MIT}) press}" } -> publisher      * \@String { anct      = "Anecdote" } -> other      * \@String { eg        = "for example" } -> other      * \@String { et        = " and " } -> other      * \@String { lBigMac   = "Big Mac" } -> other      *<p>      * Usage:      *<p>      * \@Misc {      * title       = "The GNU Project"      * author      = aStallman # et # aKahle      * institution = iMIT      * publisher   = pMIT      * note        = "Just " # eg      * }      *      * @author Jan Kubovy<jan@kubovy.eu>      */
DECL|enum|Type
specifier|public
enum|enum
name|Type
block|{
DECL|enumConstant|AUTHOR
name|AUTHOR
argument_list|(
literal|"a"
argument_list|)
block|,
DECL|enumConstant|INSTITUTION
name|INSTITUTION
argument_list|(
literal|"i"
argument_list|)
block|,
DECL|enumConstant|PUBLISHER
name|PUBLISHER
argument_list|(
literal|"p"
argument_list|)
block|,
DECL|enumConstant|OTHER
name|OTHER
argument_list|(
literal|""
argument_list|)
block|;
DECL|field|prefix
specifier|private
specifier|final
name|String
name|prefix
decl_stmt|;
DECL|method|Type (String prefix)
name|Type
parameter_list|(
name|String
name|prefix
parameter_list|)
block|{
name|this
operator|.
name|prefix
operator|=
name|prefix
expr_stmt|;
block|}
DECL|method|get (String name)
specifier|public
specifier|static
name|Type
name|get
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|name
operator|.
name|length
argument_list|()
operator|<=
literal|1
condition|)
block|{
return|return
name|OTHER
return|;
block|}
if|if
condition|(
operator|!
operator|(
name|String
operator|.
name|valueOf
argument_list|(
name|name
operator|.
name|charAt
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|)
operator|.
name|toUpperCase
argument_list|()
operator|.
name|equals
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|name
operator|.
name|charAt
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|OTHER
return|;
block|}
for|for
control|(
name|Type
name|t
range|:
name|Type
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|t
operator|.
name|prefix
operator|.
name|equals
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|name
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|t
return|;
block|}
block|}
return|return
name|OTHER
return|;
block|}
block|}
DECL|field|name
specifier|private
name|String
name|name
decl_stmt|;
DECL|field|content
specifier|private
name|String
name|content
decl_stmt|;
DECL|field|id
specifier|private
name|String
name|id
decl_stmt|;
DECL|field|type
specifier|private
name|Type
name|type
decl_stmt|;
DECL|field|parsedSerialization
specifier|private
name|String
name|parsedSerialization
decl_stmt|;
DECL|field|hasChanged
specifier|private
name|boolean
name|hasChanged
decl_stmt|;
DECL|method|BibtexString (String id, String name, String content)
specifier|public
name|BibtexString
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|content
operator|=
name|content
expr_stmt|;
name|hasChanged
operator|=
literal|true
expr_stmt|;
name|type
operator|=
name|Type
operator|.
name|get
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
DECL|method|getId ()
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
DECL|method|setId (String id)
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
name|hasChanged
operator|=
literal|true
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
DECL|method|setName (String name)
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|hasChanged
operator|=
literal|true
expr_stmt|;
name|type
operator|=
name|Type
operator|.
name|get
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
DECL|method|getContent ()
specifier|public
name|String
name|getContent
parameter_list|()
block|{
return|return
name|content
operator|==
literal|null
condition|?
literal|""
else|:
name|content
return|;
block|}
DECL|method|setContent (String content)
specifier|public
name|void
name|setContent
parameter_list|(
name|String
name|content
parameter_list|)
block|{
name|this
operator|.
name|content
operator|=
name|content
expr_stmt|;
name|hasChanged
operator|=
literal|true
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|clone ()
specifier|public
name|Object
name|clone
parameter_list|()
block|{
return|return
operator|new
name|BibtexString
argument_list|(
name|id
argument_list|,
name|name
argument_list|,
name|content
argument_list|)
return|;
block|}
DECL|method|getType ()
specifier|public
name|Type
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
DECL|method|setParsedSerialization (String parsedSerialization)
specifier|public
name|void
name|setParsedSerialization
parameter_list|(
name|String
name|parsedSerialization
parameter_list|)
block|{
name|this
operator|.
name|parsedSerialization
operator|=
name|parsedSerialization
expr_stmt|;
name|hasChanged
operator|=
literal|false
expr_stmt|;
block|}
DECL|method|getParsedSerialization ()
specifier|public
name|String
name|getParsedSerialization
parameter_list|()
block|{
return|return
name|parsedSerialization
return|;
block|}
DECL|method|hasChanged ()
specifier|public
name|boolean
name|hasChanged
parameter_list|()
block|{
return|return
name|hasChanged
return|;
block|}
block|}
end_class

end_unit

