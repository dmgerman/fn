begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2003 Morten O. Alver and Nizar N. Batada  *  * All programs in this directory and subdirectories are published under the GNU  * General Public License as described below.  *  * This program is free software; you can redistribute it and/or modify it under  * the terms of the GNU General Public License as published by the Free Software  * Foundation; either version 2 of the License, or (at your option) any later  * version.  *  * This program is distributed in the hope that it will be useful, but WITHOUT  * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS  * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more  * details.  *  * You should have received a copy of the GNU General Public License along with  * this program; if not, write to the Free Software Foundation, Inc., 59 Temple  * Place, Suite 330, Boston, MA 02111-1307 USA  *  * Further information about the GNU GPL is available at:  * http://www.gnu.org/copyleft/gpl.ja.html  *  */
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexDatabase
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
name|BibtexEntry
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
name|BibtexEntryType
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
name|GUIGlobals
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
name|KeyCollisionException
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
name|Util
import|;
end_import

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
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileReader
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
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
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

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|HttpURLConnection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|Set
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
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_comment
comment|/*  * // int jabrefframe BibtexDatabase database=new BibtexDatabase(); String  * filename=Globals.getNewFile(); ArrayList bibitems=readISI(filename); // is  * there a getFileName(); Iterator it = bibitems.iterator();  * while(it.hasNext()){ BibtexEntry entry = (BibtexEntry)it.next();  * entry.setId(Util.createId(entry.getType(), database); try {  * database.insertEntry(entry); } catch (KeyCollisionException ex) {  *  } }  */
end_comment

begin_class
DECL|class|ImportFormatReader
specifier|public
class|class
name|ImportFormatReader
block|{
DECL|field|formats
specifier|private
name|TreeMap
name|formats
init|=
operator|new
name|TreeMap
argument_list|()
decl_stmt|;
DECL|method|ImportFormatReader ()
specifier|public
name|ImportFormatReader
parameter_list|()
block|{
comment|// Add all our importers to the TreeMap. The map is used to build the import
comment|// menus, and to resolve command-line import instructions.
name|formats
operator|.
name|put
argument_list|(
literal|"isi"
argument_list|,
operator|new
name|IsiImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|put
argument_list|(
literal|"refer"
argument_list|,
operator|new
name|EndnoteImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|put
argument_list|(
literal|"medline"
argument_list|,
operator|new
name|MedlineImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|put
argument_list|(
literal|"bibtexml"
argument_list|,
operator|new
name|BibteXMLImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|put
argument_list|(
literal|"biblioscape"
argument_list|,
operator|new
name|BiblioscapeImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|put
argument_list|(
literal|"sixpack"
argument_list|,
operator|new
name|SixpackImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|put
argument_list|(
literal|"inspec"
argument_list|,
operator|new
name|InspecImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|put
argument_list|(
literal|"scifinder"
argument_list|,
operator|new
name|ScifinderImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|put
argument_list|(
literal|"ovid"
argument_list|,
operator|new
name|OvidImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|put
argument_list|(
literal|"ris"
argument_list|,
operator|new
name|RisImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|put
argument_list|(
literal|"jstor"
argument_list|,
operator|new
name|JstorImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|put
argument_list|(
literal|"silverplatter"
argument_list|,
operator|new
name|SilverPlatterImporter
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|importFromStream (String format, InputStream in)
specifier|public
name|List
name|importFromStream
parameter_list|(
name|String
name|format
parameter_list|,
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
block|{
name|Object
name|o
init|=
name|formats
operator|.
name|get
argument_list|(
name|format
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unknown import format: "
operator|+
name|format
argument_list|)
throw|;
name|ImportFormat
name|importer
init|=
operator|(
name|ImportFormat
operator|)
name|o
decl_stmt|;
name|List
name|res
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|in
argument_list|)
decl_stmt|;
comment|// Remove all empty entries
if|if
condition|(
name|res
operator|!=
literal|null
condition|)
name|purgeEmptyEntries
argument_list|(
name|res
argument_list|)
expr_stmt|;
return|return
name|res
return|;
block|}
DECL|method|importFromFile (String format, String filename)
specifier|public
name|List
name|importFromFile
parameter_list|(
name|String
name|format
parameter_list|,
name|String
name|filename
parameter_list|)
throws|throws
name|IOException
block|{
name|Object
name|o
init|=
name|formats
operator|.
name|get
argument_list|(
name|format
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unknown import format: "
operator|+
name|format
argument_list|)
throw|;
name|ImportFormat
name|importer
init|=
operator|(
name|ImportFormat
operator|)
name|o
decl_stmt|;
comment|//System.out.println(importer.getFormatName());
return|return
name|importFromFile
argument_list|(
name|importer
argument_list|,
name|filename
argument_list|)
return|;
block|}
DECL|method|importFromFile (ImportFormat importer, String filename)
specifier|public
name|List
name|importFromFile
parameter_list|(
name|ImportFormat
name|importer
parameter_list|,
name|String
name|filename
parameter_list|)
throws|throws
name|IOException
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|filename
argument_list|)
decl_stmt|;
name|InputStream
name|stream
init|=
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|stream
argument_list|)
condition|)
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Wrong file format"
argument_list|)
argument_list|)
throw|;
name|stream
operator|=
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
expr_stmt|;
return|return
name|importer
operator|.
name|importEntries
argument_list|(
name|stream
argument_list|)
return|;
block|}
DECL|method|createDatabase (List bibentries)
specifier|public
specifier|static
name|BibtexDatabase
name|createDatabase
parameter_list|(
name|List
name|bibentries
parameter_list|)
block|{
name|purgeEmptyEntries
argument_list|(
name|bibentries
argument_list|)
expr_stmt|;
name|BibtexDatabase
name|database
init|=
operator|new
name|BibtexDatabase
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|bibentries
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|BibtexEntry
name|entry
init|=
operator|(
name|BibtexEntry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
try|try
block|{
name|entry
operator|.
name|setId
argument_list|(
name|Util
operator|.
name|createId
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{
comment|//ignore
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"KeyCollisionException [ addBibEntries(...) ]"
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|database
return|;
block|}
DECL|method|getImportFormats ()
specifier|public
name|Set
name|getImportFormats
parameter_list|()
block|{
return|return
name|formats
operator|.
name|entrySet
argument_list|()
return|;
block|}
DECL|method|getImportFormatList ()
specifier|public
name|String
name|getImportFormatList
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
name|Iterator
name|i
init|=
name|formats
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|format
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|ImportFormat
name|imFo
init|=
operator|(
name|ImportFormat
operator|)
name|formats
operator|.
name|get
argument_list|(
name|format
argument_list|)
decl_stmt|;
name|int
name|pad
init|=
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
literal|14
operator|-
name|imFo
operator|.
name|getFormatName
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"  "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|imFo
operator|.
name|getFormatName
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|pad
condition|;
name|j
operator|++
control|)
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" : "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|format
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
name|String
name|res
init|=
name|sb
operator|.
name|toString
argument_list|()
decl_stmt|;
return|return
name|res
return|;
comment|//.substring(0, res.length()-1);
block|}
comment|/**    * Describe<code>fixAuthor</code> method here.    *    * @param in    *          a<code>String</code> value    * @return a<code>String</code> value // input format string: LN FN [and    *         LN, FN]* // output format string: FN LN [and FN LN]*    */
DECL|method|fixAuthor_nocomma (String in)
specifier|public
specifier|static
name|String
name|fixAuthor_nocomma
parameter_list|(
name|String
name|in
parameter_list|)
block|{
return|return
name|fixAuthor
argument_list|(
name|in
argument_list|)
return|;
comment|/*      * // Check if we have cached this particular name string before: Object old =      * Globals.nameCache.get(in); if (old != null) return (String)old;      *      * StringBuffer sb=new StringBuffer(); String[] authors = in.split(" and ");      * for(int i=0; i<authors.length; i++){ //System.out.println(authors[i]);      * authors[i]=authors[i].trim(); String[] t = authors[i].split(" "); if      * (t.length> 1) { sb.append(t[t.length-1].trim()); for (int cnt=0; cnt      *<=t.length-2; cnt++) sb.append(" " + t[cnt].trim()); } else      * sb.append(t[0].trim()); if(i==authors.length-1) sb.append("."); else      * sb.append(" and ");      *  }      *      * String fixed = sb.toString();      *  // Add the fixed name string to the cache. Globals.nameCache.put(in,      * fixed);      *      * return fixed;      */
block|}
comment|//========================================================
comment|// rearranges the author names
comment|// input format string: LN, FN [and LN, FN]*
comment|// output format string: FN LN [, FN LN]+ [and FN LN]
comment|//========================================================
DECL|method|fixAuthor_commas (String in)
specifier|public
specifier|static
name|String
name|fixAuthor_commas
parameter_list|(
name|String
name|in
parameter_list|)
block|{
return|return
operator|(
name|fixAuthor
argument_list|(
name|in
argument_list|,
literal|false
argument_list|)
operator|)
return|;
block|}
comment|//========================================================
comment|// rearranges the author names
comment|// input format string: LN, FN [and LN, FN]*
comment|// output format string: FN LN [and FN LN]*
comment|//========================================================
DECL|method|fixAuthor (String in)
specifier|public
specifier|static
name|String
name|fixAuthor
parameter_list|(
name|String
name|in
parameter_list|)
block|{
return|return
operator|(
name|fixAuthor
argument_list|(
name|in
argument_list|,
literal|true
argument_list|)
operator|)
return|;
block|}
DECL|method|fixAuthor (String in, boolean includeAnds)
specifier|public
specifier|static
name|String
name|fixAuthor
parameter_list|(
name|String
name|in
parameter_list|,
name|boolean
name|includeAnds
parameter_list|)
block|{
comment|//Util.pr("firstnamefirst");
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
comment|//System.out.println("FIX AUTHOR: in= " + in);
name|String
index|[]
name|authors
init|=
name|in
operator|.
name|split
argument_list|(
literal|" and "
argument_list|)
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
name|authors
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|authors
index|[
name|i
index|]
operator|=
name|authors
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
name|String
index|[]
name|t
init|=
name|authors
index|[
name|i
index|]
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
if|if
condition|(
name|t
operator|.
name|length
operator|<
literal|2
condition|)
comment|// there is no comma, assume we have FN LN order
name|sb
operator|.
name|append
argument_list|(
name|authors
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|sb
operator|.
name|append
argument_list|(
name|t
index|[
literal|1
index|]
operator|.
name|trim
argument_list|()
operator|+
literal|" "
operator|+
name|t
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|includeAnds
condition|)
block|{
if|if
condition|(
name|i
operator|!=
operator|(
name|authors
operator|.
name|length
operator|-
literal|1
operator|)
condition|)
comment|// put back the " and "
name|sb
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|i
operator|==
operator|(
name|authors
operator|.
name|length
operator|-
literal|2
operator|)
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|i
operator|!=
operator|(
name|authors
operator|.
name|length
operator|-
literal|1
operator|)
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
block|}
name|String
name|fixed
init|=
name|sb
operator|.
name|toString
argument_list|()
decl_stmt|;
return|return
name|fixed
return|;
block|}
comment|//========================================================
comment|// rearranges the author names
comment|// input format string: LN, FN [and LN, FN]*
comment|// output format string: LN, FN [and LN, FN]*
comment|//========================================================
DECL|method|fixAuthor_lastnameFirst (String in)
specifier|public
specifier|static
name|String
name|fixAuthor_lastnameFirst
parameter_list|(
name|String
name|in
parameter_list|)
block|{
comment|//Util.pr("lastnamefirst: in");
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|String
index|[]
name|authors
init|=
name|in
operator|.
name|split
argument_list|(
literal|" and "
argument_list|)
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
name|authors
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|authors
index|[
name|i
index|]
operator|=
name|authors
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
name|int
name|comma
init|=
name|authors
index|[
name|i
index|]
operator|.
name|indexOf
argument_list|(
literal|','
argument_list|)
decl_stmt|;
name|test
label|:
if|if
condition|(
name|comma
operator|>=
literal|0
condition|)
comment|// There is a comma, so we assume it's ok.
name|sb
operator|.
name|append
argument_list|(
name|authors
index|[
name|i
index|]
argument_list|)
expr_stmt|;
else|else
block|{
comment|// The name is without a comma, so it must be rearranged.
name|int
name|pos
init|=
name|authors
index|[
name|i
index|]
operator|.
name|lastIndexOf
argument_list|(
literal|' '
argument_list|)
decl_stmt|;
if|if
condition|(
name|pos
operator|==
operator|-
literal|1
condition|)
block|{
comment|// No spaces. Give up and just add the name.
name|sb
operator|.
name|append
argument_list|(
name|authors
index|[
name|i
index|]
argument_list|)
expr_stmt|;
break|break
name|test
break|;
block|}
name|String
name|surname
init|=
name|authors
index|[
name|i
index|]
operator|.
name|substring
argument_list|(
name|pos
operator|+
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|surname
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"jr."
argument_list|)
condition|)
block|{
name|pos
operator|=
name|authors
index|[
name|i
index|]
operator|.
name|lastIndexOf
argument_list|(
literal|' '
argument_list|,
name|pos
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|pos
operator|==
operator|-
literal|1
condition|)
block|{
comment|// Only last name and jr?
name|sb
operator|.
name|append
argument_list|(
name|authors
index|[
name|i
index|]
argument_list|)
expr_stmt|;
break|break
name|test
break|;
block|}
else|else
name|surname
operator|=
name|authors
index|[
name|i
index|]
operator|.
name|substring
argument_list|(
name|pos
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
comment|// Ok, we've isolated the last name. Put together the rearranged name:
name|sb
operator|.
name|append
argument_list|(
name|surname
operator|+
literal|", "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|authors
index|[
name|i
index|]
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|pos
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|i
operator|!=
operator|(
name|authors
operator|.
name|length
operator|-
literal|1
operator|)
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
comment|/*      * String[] t = authors[i].split(","); if(t.length< 2) { // The name is      * without a comma, so it must be rearranged. t = authors[i].split(" "); if      * (t.length> 1) { sb.append(t[t.length - 1]+ ","); // Last name for (int      * j=0; j<t.length-1; j++) sb.append(" "+t[j]); } else if (t.length> 0)      * sb.append(t[0]); } else { // The name is written with last name first, so      * it's ok. sb.append(authors[i]); }      *      * if(i !=authors.length-1) sb.append(" and ");      *  }      */
comment|//Util.pr(in+" -> "+sb.toString());
name|String
name|fixed
init|=
name|sb
operator|.
name|toString
argument_list|()
decl_stmt|;
return|return
name|fixed
return|;
block|}
DECL|method|checkAndCreateFile (String filename)
specifier|static
name|File
name|checkAndCreateFile
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|filename
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|f
operator|.
name|exists
argument_list|()
operator|&&
operator|!
name|f
operator|.
name|canRead
argument_list|()
operator|&&
operator|!
name|f
operator|.
name|isFile
argument_list|()
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Error "
operator|+
name|filename
operator|+
literal|" is not a valid file and|or is not readable."
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|logger
argument_list|(
literal|"Error "
operator|+
name|filename
operator|+
literal|" is not a valid file and|or is not readable."
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
return|return
name|f
return|;
block|}
comment|//==================================================
comment|// Set a field, unless the string to set is empty.
comment|//==================================================
DECL|method|setIfNecessary (BibtexEntry be, String field, String content)
specifier|public
specifier|static
name|void
name|setIfNecessary
parameter_list|(
name|BibtexEntry
name|be
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|content
parameter_list|)
block|{
if|if
condition|(
operator|!
name|content
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|be
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
DECL|method|loadDatabase (File fileToOpen, String encoding)
specifier|public
specifier|static
name|ParserResult
name|loadDatabase
parameter_list|(
name|File
name|fileToOpen
parameter_list|,
name|String
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Temporary (old method):
comment|//FileLoader fl = new FileLoader();
comment|//BibtexDatabase db = fl.load(fileToOpen.getPath());
name|Reader
name|reader
init|=
name|getReader
argument_list|(
name|fileToOpen
argument_list|,
name|encoding
argument_list|)
decl_stmt|;
name|String
name|suppliedEncoding
init|=
literal|null
decl_stmt|;
try|try
block|{
name|boolean
name|keepon
init|=
literal|true
decl_stmt|;
name|int
name|piv
init|=
literal|0
decl_stmt|;
name|int
name|c
decl_stmt|;
while|while
condition|(
name|keepon
condition|)
block|{
name|c
operator|=
name|reader
operator|.
name|read
argument_list|()
expr_stmt|;
if|if
condition|(
operator|(
operator|(
name|piv
operator|==
literal|0
operator|)
operator|&&
name|Character
operator|.
name|isWhitespace
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
operator|)
operator|||
operator|(
name|c
operator|==
name|GUIGlobals
operator|.
name|SIGNATURE
operator|.
name|charAt
argument_list|(
name|piv
argument_list|)
operator|)
condition|)
name|piv
operator|++
expr_stmt|;
else|else
name|keepon
operator|=
literal|false
expr_stmt|;
name|found
label|:
if|if
condition|(
name|piv
operator|==
name|GUIGlobals
operator|.
name|SIGNATURE
operator|.
name|length
argument_list|()
condition|)
block|{
name|keepon
operator|=
literal|false
expr_stmt|;
comment|// Found the signature. The rest of the line is unknown, so we skip
comment|// it:
while|while
condition|(
name|reader
operator|.
name|read
argument_list|()
operator|!=
literal|'\n'
condition|)
empty_stmt|;
comment|// Then we must skip the "Encoding: "
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|GUIGlobals
operator|.
name|encPrefix
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|reader
operator|.
name|read
argument_list|()
operator|!=
name|GUIGlobals
operator|.
name|encPrefix
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
condition|)
break|break
name|found
break|;
comment|// No,
comment|// it
comment|// doesn't
comment|// seem
comment|// to
comment|// match.
block|}
comment|// If ok, then read the rest of the line, which should contain the
comment|// name
comment|// of the encoding:
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
while|while
condition|(
operator|(
name|c
operator|=
name|reader
operator|.
name|read
argument_list|()
operator|)
operator|!=
literal|'\n'
condition|)
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
name|suppliedEncoding
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{     }
if|if
condition|(
operator|(
name|suppliedEncoding
operator|!=
literal|null
operator|)
operator|&&
operator|(
operator|!
name|suppliedEncoding
operator|.
name|equalsIgnoreCase
argument_list|(
name|encoding
argument_list|)
operator|)
condition|)
block|{
name|Reader
name|oldReader
init|=
name|reader
decl_stmt|;
try|try
block|{
comment|// Ok, the supplied encoding is different from our default, so we must
comment|// make a new
comment|// reader. Then close the old one.
name|reader
operator|=
name|getReader
argument_list|(
name|fileToOpen
argument_list|,
name|suppliedEncoding
argument_list|)
expr_stmt|;
name|oldReader
operator|.
name|close
argument_list|()
expr_stmt|;
comment|//System.out.println("Using encoding: "+suppliedEncoding);
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|reader
operator|=
name|oldReader
expr_stmt|;
comment|// The supplied encoding didn't work out, so we keep
comment|// our
comment|// existing reader.
comment|//System.out.println("Error, using default encoding.");
block|}
block|}
else|else
block|{
comment|// We couldn't find a supplied encoding. Since we don't know far into the
comment|// file we read,
comment|// we start a new reader.
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
name|reader
operator|=
name|getReader
argument_list|(
name|fileToOpen
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
comment|//System.out.println("No encoding supplied, or supplied encoding equals
comment|// default. Using default encoding.");
block|}
comment|//return null;
name|BibtexParser
name|bp
init|=
operator|new
name|BibtexParser
argument_list|(
name|reader
argument_list|)
decl_stmt|;
name|ParserResult
name|pr
init|=
name|bp
operator|.
name|parse
argument_list|()
decl_stmt|;
name|pr
operator|.
name|setEncoding
argument_list|(
name|encoding
argument_list|)
expr_stmt|;
return|return
name|pr
return|;
block|}
DECL|method|getReader (File f, String encoding)
specifier|public
specifier|static
name|Reader
name|getReader
parameter_list|(
name|File
name|f
parameter_list|,
name|String
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
name|InputStreamReader
name|reader
decl_stmt|;
name|reader
operator|=
operator|new
name|InputStreamReader
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|f
argument_list|)
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
return|return
name|reader
return|;
block|}
DECL|method|getReaderDefaultEncoding (InputStream in)
specifier|public
specifier|static
name|Reader
name|getReaderDefaultEncoding
parameter_list|(
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
block|{
name|InputStreamReader
name|reader
decl_stmt|;
name|reader
operator|=
operator|new
name|InputStreamReader
argument_list|(
name|in
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"defaultEncoding"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|reader
return|;
block|}
DECL|method|import_File (String format, String filename)
specifier|public
specifier|static
name|BibtexDatabase
name|import_File
parameter_list|(
name|String
name|format
parameter_list|,
name|String
name|filename
parameter_list|)
throws|throws
name|IOException
block|{
name|BibtexDatabase
name|database
init|=
literal|null
decl_stmt|;
name|List
name|bibentries
init|=
literal|null
decl_stmt|;
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|filename
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|f
operator|.
name|exists
argument_list|()
condition|)
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"File not found"
argument_list|)
operator|+
literal|": "
operator|+
name|filename
argument_list|)
throw|;
try|try
block|{
name|bibentries
operator|=
name|Globals
operator|.
name|importFormatReader
operator|.
name|importFromFile
argument_list|(
name|format
argument_list|,
name|filename
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not resolve import format"
argument_list|)
operator|+
literal|" '"
operator|+
name|format
operator|+
literal|"'"
argument_list|)
throw|;
block|}
if|if
condition|(
name|bibentries
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Import failed"
argument_list|)
argument_list|)
throw|;
comment|// Remove all empty entries:
name|purgeEmptyEntries
argument_list|(
name|bibentries
argument_list|)
expr_stmt|;
comment|// Add entries to database.
name|database
operator|=
operator|new
name|BibtexDatabase
argument_list|()
expr_stmt|;
name|Iterator
name|it
init|=
name|bibentries
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|BibtexEntry
name|entry
init|=
operator|(
name|BibtexEntry
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
try|try
block|{
name|entry
operator|.
name|setId
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{
comment|//ignore
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"KeyCollisionException [ addBibEntries(...) ]"
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|database
return|;
block|}
comment|/**    * Receives an ArrayList of BibtexEntry instances, iterates through them, and    * removes all entries that have no fields set. This is useful for rooting out    * an unsucessful import (wrong format) that returns a number of empty entries.    */
DECL|method|purgeEmptyEntries (List entries)
specifier|public
specifier|static
name|void
name|purgeEmptyEntries
parameter_list|(
name|List
name|entries
parameter_list|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|entries
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|BibtexEntry
name|entry
init|=
operator|(
name|BibtexEntry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
comment|// Get all fields of the entry:
name|Object
index|[]
name|o
init|=
name|entry
operator|.
name|getAllFields
argument_list|()
decl_stmt|;
comment|// If there are no fields, remove the entry:
if|if
condition|(
name|o
operator|.
name|length
operator|==
literal|0
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**    * Tries to import a file by iterating through the available import filters,    * and keeping the import that seems most promising. Returns an Object array    * with two elements, 0: the name of the format used, 1: a List of entries.    */
DECL|method|importUnknownFormat (String filename)
specifier|public
name|Object
index|[]
name|importUnknownFormat
parameter_list|(
name|String
name|filename
parameter_list|)
throws|throws
name|IOException
block|{
name|Object
name|entryList
init|=
literal|null
decl_stmt|;
name|String
name|usedFormat
init|=
literal|null
decl_stmt|;
name|int
name|bestResult
init|=
literal|0
decl_stmt|;
comment|// Cycle through all importers:
for|for
control|(
name|Iterator
name|i
init|=
name|getImportFormats
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ImportFormat
name|imFo
init|=
call|(
name|ImportFormat
call|)
argument_list|(
operator|(
name|Map
operator|.
name|Entry
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|)
operator|.
name|getValue
argument_list|()
decl_stmt|;
try|try
block|{
comment|//System.out.println("Trying format: "+imFo.getFormatName());
name|List
name|entries
init|=
name|importFromFile
argument_list|(
name|imFo
argument_list|,
name|filename
argument_list|)
decl_stmt|;
if|if
condition|(
name|entries
operator|!=
literal|null
condition|)
name|purgeEmptyEntries
argument_list|(
name|entries
argument_list|)
expr_stmt|;
name|int
name|entryCount
init|=
operator|(
operator|(
name|entries
operator|!=
literal|null
operator|)
condition|?
name|entries
operator|.
name|size
argument_list|()
else|:
literal|0
operator|)
decl_stmt|;
comment|//System.out.println("Entries: "+entryCount);
comment|//BibtexDatabase base = importFile(formats[i], filename);
if|if
condition|(
name|entryCount
operator|>
name|bestResult
condition|)
block|{
name|bestResult
operator|=
name|entryCount
expr_stmt|;
name|usedFormat
operator|=
name|imFo
operator|.
name|getFormatName
argument_list|()
expr_stmt|;
name|entryList
operator|=
name|entries
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
comment|//ex.printStackTrace();
comment|//System.out.println("Import failed");
block|}
block|}
comment|// Finally, if all else fails, see if it is a BibTeX file:
if|if
condition|(
name|entryList
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|ParserResult
name|pr
init|=
name|loadDatabase
argument_list|(
operator|new
name|File
argument_list|(
name|filename
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"defaultEncoding"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
operator|>
literal|0
operator|)
operator|||
operator|(
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getStringCount
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|entryList
operator|=
name|pr
expr_stmt|;
name|usedFormat
operator|=
literal|"BibTeX"
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
comment|//ex.printStackTrace();
block|}
block|}
return|return
operator|new
name|Object
index|[]
block|{
name|usedFormat
block|,
name|entryList
block|}
return|;
block|}
block|}
end_class

end_unit

