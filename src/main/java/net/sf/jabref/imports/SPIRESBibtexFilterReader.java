begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FilterReader
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
name|Reader
import|;
end_import

begin_comment
comment|/**  *   * Warning -- it is not a generic filter, only read is implemented!  *   * @author Fedor Bezrukov  *   * @version $Id$  *   * TODO: Fix grammar in bibtex entries -- it ma return invalid bibkeys (with space)  *   */
end_comment

begin_class
DECL|class|SPIRESBibtexFilterReader
specifier|public
class|class
name|SPIRESBibtexFilterReader
extends|extends
name|FilterReader
block|{
DECL|field|in
specifier|protected
specifier|final
name|BufferedReader
name|in
decl_stmt|;
DECL|field|line
specifier|private
name|String
name|line
decl_stmt|;
DECL|field|pos
specifier|private
name|int
name|pos
decl_stmt|;
DECL|field|pre
specifier|private
name|boolean
name|pre
decl_stmt|;
DECL|method|SPIRESBibtexFilterReader (Reader _in)
name|SPIRESBibtexFilterReader
parameter_list|(
name|Reader
name|_in
parameter_list|)
block|{
name|super
argument_list|(
name|_in
argument_list|)
expr_stmt|;
name|in
operator|=
operator|new
name|BufferedReader
argument_list|(
name|_in
argument_list|)
expr_stmt|;
name|pos
operator|=
operator|-
literal|1
expr_stmt|;
name|pre
operator|=
literal|false
expr_stmt|;
block|}
DECL|method|readpreLine ()
specifier|private
name|String
name|readpreLine
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|l
decl_stmt|;
do|do
block|{
name|l
operator|=
name|in
operator|.
name|readLine
argument_list|()
expr_stmt|;
if|if
condition|(
name|l
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|l
operator|.
name|equals
argument_list|(
literal|"<pre>"
argument_list|)
condition|)
block|{
name|pre
operator|=
literal|true
expr_stmt|;
name|l
operator|=
name|in
operator|.
name|readLine
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|l
operator|.
name|equals
argument_list|(
literal|"</pre>"
argument_list|)
condition|)
block|{
name|pre
operator|=
literal|false
expr_stmt|;
block|}
block|}
do|while
condition|(
operator|!
name|pre
condition|)
do|;
return|return
name|l
return|;
block|}
DECL|method|fixBibkey (String in)
specifier|private
name|String
name|fixBibkey
parameter_list|(
name|String
name|in
parameter_list|)
block|{
if|if
condition|(
name|in
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
comment|//System.out.println(in);
if|if
condition|(
name|in
operator|.
name|matches
argument_list|(
literal|"@Article\\{.*,"
argument_list|)
condition|)
block|{
comment|//System.out.println(in.replace(' ','_'));
return|return
name|in
operator|.
name|replace
argument_list|(
literal|' '
argument_list|,
literal|'_'
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|in
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|read ()
specifier|public
name|int
name|read
parameter_list|()
throws|throws
name|IOException
block|{
if|if
condition|(
name|pos
operator|<
literal|0
condition|)
block|{
name|line
operator|=
name|fixBibkey
argument_list|(
name|readpreLine
argument_list|()
argument_list|)
expr_stmt|;
name|pos
operator|=
literal|0
expr_stmt|;
if|if
condition|(
name|line
operator|==
literal|null
condition|)
block|{
return|return
operator|-
literal|1
return|;
block|}
block|}
if|if
condition|(
name|pos
operator|>=
name|line
operator|.
name|length
argument_list|()
condition|)
block|{
name|pos
operator|=
operator|-
literal|1
expr_stmt|;
return|return
literal|'\n'
return|;
block|}
return|return
name|line
operator|.
name|charAt
argument_list|(
name|pos
operator|++
argument_list|)
return|;
block|}
block|}
end_class

end_unit

