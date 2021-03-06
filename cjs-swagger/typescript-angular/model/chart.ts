/**
 * Chart API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { DataSet } from './dataSet';


export interface Chart {
    labels: Array<string>;
    datasets: Array<DataSet>;
    type?: string;
    yaxesLabel?: string;
    xaxesLabel?: string;
}
