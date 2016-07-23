import java.io.{ByteArrayInputStream, InputStream}

object TestHelpers {
    def getTestGatewayJSON: InputStream = {
        new ByteArrayInputStream("""
                                   |{
                                   |"body":{
                                   |"token":"sf0Rq4MMxUUSnTK29cknMRHI",
                                   |"team_id":"T0001",
                                   |"team_domain":"example",
                                   |"channel_id":"C2147483705",
                                   |"channel_name":"test",
                                   |"user_id":"U2147483697",
                                   |"user_name":"Steve",
                                   |"command":"/weather",
                                   |"text":"digraph G{\nA -> B;\nC -> B;\n}",
                                   |"response_url":"https://hooks.slack.com/commands/1234/5678"
                                   |},
                                   |"official_slack_token":"sf0Rq4MMxUUSnTK29cknMRHI"
                                   |}
                                 """.stripMargin.getBytes())
    }

    def getTestGatewayJSONBadRequest: InputStream = {
        new ByteArrayInputStream("""
                                   |{
                                   |"body":{
                                   |"token":"sf0Rq4MMxUUSnTK29cknMRHI",
                                   |"team_id":"T0001",
                                   |"team_domain":"example",
                                   |"channel_id":"C2147483705",
                                   |"channel_name":"test",
                                   |"user_id":"U2147483697",
                                   |"user_name":"Steve",
                                   |"command":"/weather",
                                   |"text":"digraph G{\nA -> B; -> B;\n}",
                                   |"response_url":"https://hooks.slack.com/commands/1234/5678"
                                   |},
                                   |"official_slack_token":"sf0Rq4MMxUUSnTK29cknMRHI"
                                   |}
                                 """.stripMargin.getBytes())
    }

    def getTestGatewayJSONBadJsonFormat: InputStream = {
        new ByteArrayInputStream("""
                                   |{
                                   |"body":{
                                   |"token":"sf0Rq4MMxUUSnTK29cknMRHI",
                                   |"team_id":"T0001",
                                   |"team_domain":"example",
                                   |"channel_id":"C2147483705",
                                   |"channel_name":"test",
                                   |"user_id":"U2147483697"
                                   |"user_name":"Steve"
                                   |"command":"/weather"
                                   |"text":"digraph G{\nA -> B;\nC -> B;\n}",
                                   |"response_url":"https://hooks.slack.com/commands/1234/5678"
                                   |},
                                   |"official_slack_token":"sf0Rq4MMxUUSnTK29cknMRHI"
                                   |}
                                 """.stripMargin.getBytes())
    }

    def getTestGatewayJSONBadSlackToken: InputStream = {
        new ByteArrayInputStream("""
                                   |{
                                   |"body":{
                                   |"token":"foo",
                                   |"team_id":"T0001",
                                   |"team_domain":"example",
                                   |"channel_id":"C2147483705",
                                   |"channel_name":"test",
                                   |"user_id":"U2147483697",
                                   |"user_name":"Steve",
                                   |"command":"/weather",
                                   |"text":"digraph G{\nA -> B;\nC -> B;\n}",
                                   |"response_url":"https://hooks.slack.com/commands/1234/5678"
                                   |},
                                   |"official_slack_token":"sf0Rq4MMxUUSnTK29cknMRHI"
                                   |}
                                 """.stripMargin.getBytes())
    }
}
