//
// Informa -- RSS Library for Java
// Copyright (c) 2002 by Niko Schmuck
//
// Niko Schmuck
// http://sourceforge.net/projects/informa
// mailto:niko_schmuck@users.sourceforge.net
//
// This library is free software.
//
// You may redistribute it and/or modify it under the terms of the GNU
// Lesser General Public License as published by the Free Software Foundation.
//
// Version 2.1 of the license should be included with this distribution in
// the file LICENSE. If the license is not included with this distribution,
// you may find a copy at the FSF web site at 'www.gnu.org' or 'www.fsf.org',
// or you may write to the Free Software Foundation, 675 Mass Ave, Cambridge,
// MA 02139 USA.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied waranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//


// $Id: ChannelIF.java,v 1.1 2005/04/21 19:41:09 vecego Exp $

package at.newsagg.model.parser;

import java.util.Collection;
import java.util.Date;

/**
 * This interface is implemented by objects representing channels in the
 * news channel object model.</p>
 *
 * @author Niko Schmuck (niko@nava.de)
 */
public interface ChannelIF extends WithIdMIF, WithTitleMIF, WithElementsAndAttributesMIF, 
                                   WithLocationMIF, WithCreatorMIF,  
                                   WithDescriptionMIF, WithSiteMIF {

  // ----- Valid update period values

  /** Update of channel expected to be specified in number of hours */
  public static final String UPDATE_HOURLY = "hourly";

  /** Update of channel expected to be specified in number of days */
  public static final String UPDATE_DAILY = "daily";

  /** Update of channel expected to be specified in number of weeks */
  public static final String UPDATE_WEEKLY = "weekly";

  /** Update of channel expected to be specified in number of months */
  public static final String UPDATE_MONTHLY = "monthly";

  /** Update of channel expected to be specified in number of years */
  public static final String UPDATE_YEARLY = "yearly";

  // ----- accessors and mutators

  /* Dublin Core Metadata, like Creator and Subject  */

  String getLanguage();

  void setLanguage(String language);

  String getPublisher();

  void setPublisher(String publisher);

  String getRating();

  void setRating(String rating);

  String getGenerator();

  void setGenerator(String generator);

  String getDocs();

  void setDocs(String docs);

  int getTtl();

  void setTtl(int ttl);

  /**
   * Gets the syntax format used by the channel.
   *
   * @return The format of the channel as specified by
   *         the constants in {@link ChannelFormat}.
   */
  ChannelFormat getFormat();

  void setFormat(ChannelFormat format);

  /**
   * @return A collection of {@link ItemIF} objects.
   */
  Collection getItems();
  void setItems(Collection anItems);

  void addItem(ItemIF item);

  void removeItem(ItemIF item);

  /**
   * Returns the news item as specified by the item identifier
   * ({@link ItemIF#getId()}).
   * @param id the Item's id.
   * @return the Item
   */
  ItemIF getItem(long id);

  /**
   * Retrieves the Image associated with this feed. Optional
   * @return An ImageIF representing the image associated with this feed
   */
  ImageIF getImage();

  /**
   * Sets the image for this feed
   * @param image The image
   */
  void setImage(ImageIF image);

  Date getLastUpdated();

  void setLastUpdated(Date lastUpdated);

  Date getLastBuildDate();

  void setLastBuildDate(Date lastBuild);

  Date getPubDate();

  void setPubDate(Date pubDate);


  // RSS 1.0 Syndication Module methods

  /**
   * Accesses data provided by the Syndication module (will apply only
   * to RSS 1.0+). The return type will be one of:
   * <ul>
   * <li>UPDATE_HOURLY</li>
   * <li>UPDATE_DAILY</li>
   * <li>UPDATE_WEEKLY</li>
   * <li>UPDATE_MONTHLY</li>
   * <li>UPDATE_YEARLY</li>
   * <li>Null if tag not present in the RSS file</li>
   * </ul>
   * @return see above
   */
  String getUpdatePeriod();

  /**
   * Sets the update frequency for the feed. This information will be stored
   * according to the Syndication Module tags. <code>updateFrequency</code>
   * should be one of:
   * <ul>
   * <li>UPDATE_HOURLY</li>
   * <li>UPDATE_DAILY</li>
   * <li>UPDATE_WEEKLY</li>
   * <li>UPDATE_MONTHLY</li>
   * <li>UPDATE_YEARLY</li>
   * <li>Null if tag not present in the RSS file</li>
   * </ul>
   * @param updatePeriod See above
   */
  void setUpdatePeriod(String updatePeriod);

  /**
   * Accesses data provided by the Syndication module (will apply only
   * to RSS 1.0+). Returns the number of times during the
   * <code>updatePeriod</code> that a feed should be updated
   * @return The number of times during <code>updatePeriod</code> to update the
   * feed
   * @see #setUpdatePeriod
   * @see #getUpdatePeriod
   */
  int getUpdateFrequency();

  /**
   * Sets the number of times during <code>updatePeriod</code> that the feed
   * should be updated
   * @param updateFrequency number of times during <code>updatePeriod</code> to
   * update the feed
   */
  void setUpdateFrequency(int updateFrequency);

  /**
   * Accesses data provided by the Syndication module (will apply only
   * to RSS 1.0+). Provides the base date against which to determine the next
   * time to update the feed.
   * @return The date from which the next update times should be calculated
   */
  Date getUpdateBase();

  /**
   * Sets the base time against which update times should be calculated
   * @param updateBase The base date for updates
   */
  void setUpdateBase(Date updateBase);
}
