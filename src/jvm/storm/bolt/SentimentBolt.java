package storm.bolt;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.testing.TestWordSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import java.util.Map;
import storm.tools.*;


/**
 * A bolt that gets the sentiment of a tweet, then emit the sentiment with its
 * state info
 */
public class SentimentBolt extends BaseRichBolt
{
  private OutputCollector collector;
  private SentimentAnalyzer sentiment;
  @Override
  public void prepare(
      Map                     map,
      TopologyContext         topologyContext,
      OutputCollector         outputCollector)
  {
    collector = outputCollector;
    sentiment = new SentimentAnalyzer();
    sentiment.init();
  }

  @Override
  public void execute(Tuple tuple)
  {

    String tweet = tuple.getString(0);
    String state = tuple.getString(1);
    Integer tweetSentiment = sentiment.findSentiment(tweet);
    collector.emit(new Values(tweetSentiment, state));
  }

  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer)
  {
    outputFieldsDeclarer.declare(new Fields("sentiment", "state"));
  }
}
